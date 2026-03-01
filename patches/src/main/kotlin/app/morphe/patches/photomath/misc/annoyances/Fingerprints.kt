// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/photomath/misc/annoyances/Fingerprints.kt
package app.morphe.patches.photomath.misc.annoyances

import app.morphe.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val hideUpdatePopupFingerprint = fingerprint {
    accessFlags(AccessFlags.FINAL, AccessFlags.PUBLIC)
    returns("V")
    opcodes(
        Opcode.CONST_HIGH16,
        Opcode.INVOKE_VIRTUAL, // ViewPropertyAnimator.alpha(1.0f)
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.CONST_WIDE_16,
        Opcode.INVOKE_VIRTUAL, // ViewPropertyAnimator.setDuration(1000L)
    )
    custom { method, _ ->
        // The popup is shown only in the main activity
        method.definingClass == "Lcom/microblink/photomath/main/activity/MainActivity;"
    }
}
