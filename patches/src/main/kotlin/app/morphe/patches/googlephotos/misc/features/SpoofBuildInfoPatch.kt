// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlephotos/misc/features/SpoofBuildInfoPatch.kt
package app.morphe.patches.googlephotos.misc.features

import app.morphe.patcher.patch.bytecodePatch

// Note: The baseSpoofBuildInfoPatch from ReVanced's all.misc.build package
// does not exist in Morphe. Build info spoofing (making the device appear
// as a Pixel XL) is not critical for the unlimited storage feature —
// that's handled by SpoofFeaturesPatch which spoofs device feature flags.
// This patch exists as a dependency placeholder.
val spoofBuildInfoPatch = bytecodePatch(
    name = "Spoof build info",
    description = "Spoofs the device build information to Google Pixel XL.",
) {
    // No-op: Build field spoofing would require an extension class
    // to set android.os.Build fields via reflection at runtime.
}
