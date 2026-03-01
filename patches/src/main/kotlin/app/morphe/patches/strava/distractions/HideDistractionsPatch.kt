// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/strava/distractions/HideDistractionsPatch.kt
package app.morphe.patches.strava.distractions

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.booleanOption
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableClass
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod.Companion.toMutable
import app.morphe.patcher.util.proxy.mutableTypes.encodedValue.MutableBooleanEncodedValue.Companion.toMutable
import app.morphe.patches.strava.misc.extension.sharedExtensionPatch
import app.morphe.util.findMutableMethodOf
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import com.android.tools.smali.dexlib2.immutable.reference.ImmutableMethodReference
import com.android.tools.smali.dexlib2.immutable.value.ImmutableBooleanEncodedValue
import com.android.tools.smali.dexlib2.util.MethodUtil
import java.util.logging.Logger

private const val EXTENSION_CLASS_DESCRIPTOR = "Lapp/morphe/extension/strava/HideDistractionsPatch;"
private const val MODULAR_FRAMEWORK_CLASS_DESCRIPTOR_PREFIX = "Lcom/strava/modularframework"

private const val METHOD_SUFFIX = "\$original"

private data class FilterablePropertyFingerprint(
    val name: String,
    val parameterTypes: List<String> = listOf(),
)

private val fingerprints = arrayOf(
    FilterablePropertyFingerprint("ChildrenEntries"),
    FilterablePropertyFingerprint("Entries"),
    FilterablePropertyFingerprint("Field", listOf("Ljava/lang/String;")),
    FilterablePropertyFingerprint("Fields"),
    FilterablePropertyFingerprint("MenuItems"),
    FilterablePropertyFingerprint("Modules"),
    FilterablePropertyFingerprint("Properties"),
    FilterablePropertyFingerprint("StateMap"),
    FilterablePropertyFingerprint("Submodules"),
)

@Suppress("unused")
val hideDistractionsPatch = bytecodePatch(
    name = "Hide distractions",
    description = "Hides elements that are not essential.",
) {
    compatibleWith("com.strava")

    dependsOn(sharedExtensionPatch)

    val logger = Logger.getLogger(this::class.java.name)

    val options = arrayOf(
        booleanOption(
            key = "upselling",
            title = "Upselling",
            description = "Elements that suggest you subscribe.",
            default = true,
            required = true,
        ),
        booleanOption(
            key = "promo",
            title = "Promotions",
            description = "Elements that promote features, challenges, clubs, etc.",
            default = true,
            required = true,
        ),
        booleanOption(
            key = "followSuggestions",
            title = "Who to Follow",
            description = "Popular athletes, followers, people near you etc.",
            default = true,
            required = true,
        ),
        booleanOption(
            key = "challengeSuggestions",
            title = "Suggested Challenges",
            description = "Random challenges Strava wants you to join.",
            default = true,
            required = true,
        ),
        booleanOption(
            key = "joinChallenge",
            title = "Join Challenge",
            description = "Challenges your follows have joined.",
            default = false,
            required = true,
        ),
        booleanOption(
            key = "joinClub",
            title = "Joined a club",
            description = "Clubs your follows have joined.",
            default = false,
            required = true,
        ),
        booleanOption(
            key = "activityLookback",
            title = "Activity lookback",
            description = "Your activity from X years ago",
            default = false,
            required = true,
        ),
    )

    execute {
        // region Write option values into extension class.

        val extensionClass = mutableClassDefBy { it.type == EXTENSION_CLASS_DESCRIPTOR }.apply {
            options.forEach { option ->
                staticFields.first { field -> field.name == option.key }.initialValue =
                    ImmutableBooleanEncodedValue.forBoolean(option.value == true).toMutable()
            }
        }

        // endregion

        // region Intercept all classes' property getter calls.

        fun MutableMethod.cloneAndIntercept(
            classDef: MutableClass,
            extensionMethodName: String,
            extensionMethodParameterTypes: List<String>,
        ) {
            val extensionMethodReference = ImmutableMethodReference(
                EXTENSION_CLASS_DESCRIPTOR,
                extensionMethodName,
                extensionMethodParameterTypes,
                returnType,
            )

            if (extensionClass.directMethods.none { method ->
                    MethodUtil.methodSignaturesMatch(method, extensionMethodReference)
                }) {
                logger.info { "Skipped interception of $this due to missing $extensionMethodReference" }
                return
            }

            classDef.virtualMethods -= this

            val clone = ImmutableMethod.of(this).toMutable()

            classDef.virtualMethods += clone

            if (implementation != null) {
                val registers = List(extensionMethodParameterTypes.size) { index -> "p$index" }.joinToString(
                    separator = ",",
                    prefix = "{",
                    postfix = "}",
                )

                clone.addInstructions(
                    0,
                    """
                        invoke-static $registers, $extensionMethodReference
                        move-result-object v0
                        return-object v0
                    """
                )

                logger.fine { "Intercepted $this with $extensionMethodReference" }
            }

            name += METHOD_SUFFIX

            classDef.virtualMethods += this
        }

        classDefForEach { classDef ->
            if (classDef.type.startsWith(MODULAR_FRAMEWORK_CLASS_DESCRIPTOR_PREFIX)) {
                classDef.virtualMethods.forEach { method ->
                    fingerprints.find { fingerprint ->
                        method.name == "get${fingerprint.name}" && method.parameterTypes == fingerprint.parameterTypes
                    }?.let { fingerprint ->
                        mutableClassDefBy(classDef).let { mutableClass ->
                            // Upcast to the interface if this is an interface implementation.
                            val parameterType = classDef.interfaces.find {
                                var foundInterface = false
                                classDefForEach { interfaceDef ->
                                    if (interfaceDef.type == it && interfaceDef.virtualMethods.any { interfaceMethod ->
                                            MethodUtil.methodSignaturesMatch(interfaceMethod, method)
                                        }
                                    ) {
                                        foundInterface = true
                                    }
                                }
                                foundInterface
                            } ?: classDef.type

                            mutableClass.findMutableMethodOf(method).cloneAndIntercept(
                                mutableClass,
                                "filter${fingerprint.name}",
                                listOf(parameterType) + fingerprint.parameterTypes,
                            )
                        }
                    }
                }
            }
        }

        // endregion
    }
}
