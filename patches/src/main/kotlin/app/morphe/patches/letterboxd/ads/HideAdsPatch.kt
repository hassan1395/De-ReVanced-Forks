// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/letterboxd/ads/HideAdsPatch.kt
package app.morphe.patches.letterboxd.ads

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

@Suppress("unused")
val hideAdsPatch = bytecodePatch(
    name = "Hide ads",
) {
    compatibleWith("com.letterboxd.letterboxd")

    execute {
        admobHelperSetShowAdsFingerprint.method.addInstruction(0, "const/4 p1, 0x0")
        listOf(admobHelperShouldShowAdsFingerprint, filmFragmentShowAdsFingerprint, memberExtensionShowAdsFingerprint).forEach {
            it.method.returnEarly(false)
        }
    }
}
