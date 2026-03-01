// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/photomath/misc/unlock/plus/Fingerprints.kt
package app.morphe.patches.photomath.misc.unlock.plus

import com.android.tools.smali.dexlib2.AccessFlags
import app.morphe.patcher.fingerprint

internal val isPlusUnlockedFingerprint = fingerprint{
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("Z")
    strings("genius")
    custom { _, classDef ->
        classDef.endsWith("/User;")
    }
}
