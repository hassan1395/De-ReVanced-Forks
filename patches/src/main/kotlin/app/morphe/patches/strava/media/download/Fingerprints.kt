// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/strava/media/download/Fingerprints.kt
package app.morphe.patches.strava.media.download

import app.morphe.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal val createAndShowFragmentFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("V")
    parameters("L")
    strings("mediaType")
    custom { _, classDef -> classDef.type.endsWith("/FullscreenMediaFragment;") }
}

internal val handleMediaActionFingerprint = fingerprint {
    parameters("Landroid/view/View;", "Lcom/strava/bottomsheet/BottomSheetItem;")
    custom { _, classDef -> classDef.type.endsWith("/FullscreenMediaFragment;") }
}
