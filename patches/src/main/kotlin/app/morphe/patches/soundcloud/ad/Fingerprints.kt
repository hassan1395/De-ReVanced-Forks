// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/soundcloud/ad/Fingerprints.kt
package app.morphe.patches.soundcloud.ad

import app.morphe.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val interceptFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("Ljava/lang/Object;")
    parameters("Ljava/lang/Object;")
    opcodes(
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.INVOKE_INTERFACE,
        Opcode.MOVE_RESULT_OBJECT
    )
    strings("SC-Mob-UserPlan", "Configuration")
}

internal val userConsumerPlanConstructorFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    returns("V")
    parameters(
        "Ljava/lang/String;",
        "Z",
        "Ljava/lang/String;",
        "Ljava/util/List;",
        "Ljava/lang/String;",
        "Ljava/lang/String;",
    )
}
