// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/soundcloud/analytics/Fingerprints.kt
package app.morphe.patches.soundcloud.analytics

import app.morphe.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal val createTrackingApiFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC)
    returns("Ljava/lang/Object;")
    custom { method, _ ->
        method.name == "create"
    }
    strings("backend", "boogaloo")
}
