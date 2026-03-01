// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/cricbuzz/ads/DisableAdsPatch.kt
package app.morphe.patches.cricbuzz.ads

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.cricbuzz.misc.extension.sharedExtensionPatch
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstructionOrThrow
import app.morphe.util.returnEarly
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

private const val EXTENSION_CLASS_DESCRIPTOR =
    "Lapp/morphe/extension/cricbuzz/ads/HideAdsPatch;"

@Suppress("unused")
val disableAdsPatch = bytecodePatch (
    name = "Hide ads",
) {
    compatibleWith("com.cricbuzz.android"("6.24.01"))

    dependsOn(sharedExtensionPatch)

    execute {
        userStateSwitchFingerprint.method.returnEarly(true)

        // Remove region-specific Cricbuzz11 elements.
        cb11ConstructorFingerprint.method.addInstruction(0, "const/4 p7, 0x0")
        getBottomBarFingerprint.method.apply {
            val getIndex = indexOfFirstInstructionOrThrow() {
                opcode == Opcode.IGET_OBJECT && getReference<FieldReference>()?.name == "bottomBar"
            }
            val getRegister = getInstruction<TwoRegisterInstruction>(getIndex).registerA

            addInstruction(getIndex + 1,
                "invoke-static { v$getRegister }, $EXTENSION_CLASS_DESCRIPTOR->filterCb11(Ljava/util/List;)V"
            )
        }
    }
}
