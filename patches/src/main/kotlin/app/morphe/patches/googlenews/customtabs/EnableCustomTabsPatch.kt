// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlenews/customtabs/EnableCustomTabsPatch.kt
package app.morphe.patches.googlenews.customtabs

import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

@Suppress("unused")
val enableCustomTabsPatch = bytecodePatch(
    name = "Enable CustomTabs",
    description = "Enables CustomTabs to open articles in your default browser.",
) {
    compatibleWith("com.google.android.apps.magazines")

    execute {
        launchCustomTabFingerprint.method.apply {
            val impl = implementation ?: return@apply

            val pattern = listOf(
                Opcode.IPUT_OBJECT,
                Opcode.CONST_4,
                Opcode.IPUT,
                Opcode.CONST_4,
                Opcode.IPUT_BOOLEAN,
            )

            val opcodes = impl.instructions.map { it.opcode }
            val startIndex = opcodes.windowed(pattern.size).indexOfFirst { window ->
                window == pattern
            }

            if (startIndex == -1) {
                error("EnableCustomTabsPatch: fingerprint pattern not found")
            }

            val endIndex = startIndex + pattern.size - 1
            val checkIndex = endIndex + 1

            val register = getInstruction<OneRegisterInstruction>(checkIndex).registerA

            replaceInstruction(checkIndex, "const/4 v$register, 0x1")
        }
    }
}
