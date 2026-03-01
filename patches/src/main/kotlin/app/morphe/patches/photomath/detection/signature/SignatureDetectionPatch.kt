// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/photomath/detection/signature/SignatureDetectionPatch.kt
package app.morphe.patches.photomath.detection.signature

import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

val signatureDetectionPatch = bytecodePatch(
    description = "Disables detection of incorrect signature.",
) {

    execute {
        checkSignatureFingerprint.method.apply {
            val impl = implementation ?: return@apply

            val pattern = listOf(
                Opcode.CONST_STRING,
                Opcode.INVOKE_STATIC,
                Opcode.INVOKE_STATIC,
                Opcode.MOVE_RESULT_OBJECT,
                Opcode.INVOKE_VIRTUAL,
                Opcode.MOVE_RESULT_OBJECT,
                Opcode.INVOKE_STATIC,
                Opcode.MOVE_RESULT,
            )

            val opcodes = impl.instructions.map { it.opcode }
            val startIndex = opcodes.windowed(pattern.size).indexOfFirst { window ->
                window == pattern
            }

            if (startIndex == -1) {
                error("SignatureDetectionPatch: fingerprint pattern not found")
            }

            val replacementIndex = startIndex + pattern.size - 1
            val checkRegister = getInstruction<OneRegisterInstruction>(replacementIndex).registerA

            replaceInstruction(replacementIndex, "const/4 v$checkRegister, 0x1")
        }
    }
}
