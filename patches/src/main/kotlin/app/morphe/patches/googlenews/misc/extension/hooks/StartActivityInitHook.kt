// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlenews/misc/extension/hooks/StartActivityInitHook.kt
package app.morphe.patches.googlenews.misc.extension.hooks

import app.morphe.patches.shared.misc.extension.extensionHook
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstructionOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private var getApplicationContextIndex = -1

internal val startActivityInitHook = extensionHook(
    insertIndexResolver = { method ->
        getApplicationContextIndex = method.indexOfFirstInstructionOrThrow {
            getReference<MethodReference>()?.name == "getApplicationContext"
        }

        getApplicationContextIndex + 2 // Below the move-result-object instruction.
    },
    contextRegisterResolver = { method ->
        val moveResultInstruction = method.implementation!!.instructions.elementAt(getApplicationContextIndex + 1)
            as OneRegisterInstruction
        "v${moveResultInstruction.registerA}"
    },
) {
    opcodes(
        Opcode.INVOKE_STATIC,
        Opcode.MOVE_RESULT,
        Opcode.CONST_4,
        Opcode.IF_EQZ,
        Opcode.CONST,
        Opcode.INVOKE_VIRTUAL,
        Opcode.IPUT_OBJECT,
        Opcode.IPUT_BOOLEAN,
        Opcode.INVOKE_VIRTUAL, // Calls startActivity.getApplicationContext().
        Opcode.MOVE_RESULT_OBJECT,
    )
    custom { methodDef, classDef ->
        methodDef.name == "onCreate" && classDef.type.endsWith("/StartActivity;")
    }
}
