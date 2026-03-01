package app.morphe.patches.pixiv.ads

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val hideAdsPatch = bytecodePatch(
    name = "Hide ads",
) {
    compatibleWith("jp.pxv.android"("6.141.1"))

    execute {
        shouldShowAdsFingerprint.method.returnEarly(false)
    }
}
