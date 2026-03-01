// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/soundcloud/offlinesync/Fingerprints.kt
package app.morphe.patches.soundcloud.offlinesync

import app.morphe.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val downloadOperationsURLBuilderFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("Ljava/lang/String")
    parameters("Ljava/lang/Object;", "Ljava/lang/Object;")
    opcodes(
        Opcode.IGET_OBJECT,
        Opcode.SGET_OBJECT,
        Opcode.FILLED_NEW_ARRAY,
    )
}

internal val downloadOperationsHeaderVerificationFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("V")
    parameters("Ljava/lang/Object;", "Ljava/lang/Object;")
    opcodes(
        Opcode.CONST_STRING,
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.CONST_STRING,
    )
    strings("X-SC-Mime-Type", "X-SC-Preset", "X-SC-Quality")
}
