// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/cricbuzz/ads/Fingerprints.kt
package app.morphe.patches.cricbuzz.ads

import app.morphe.patcher.fingerprint
import com.android.tools.smali.dexlib2.Opcode

internal val userStateSwitchFingerprint = fingerprint {
    opcodes(Opcode.SPARSE_SWITCH)
    strings("key.user.state", "NA")
}

internal val cb11ConstructorFingerprint = fingerprint {
    parameters(
        "Ljava/lang/String;",
        "Ljava/lang/String;",
        "Ljava/lang/String;",
        "I",
        "Ljava/lang/String;",
        "Ljava/lang/String;",
        "Z",
        "Ljava/lang/String;",
        "Ljava/lang/String;",
        "L"
    )
    custom { _, classDef ->
        classDef.endsWith("CB11Details;")
    }
}

internal val getBottomBarFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getBottomBar" && classDef.endsWith("HomeMenu;")
    }
}
