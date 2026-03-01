// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlephotos/misc/extension/ExtensionPatch.kt
package app.morphe.patches.googlephotos.misc.extension

import app.morphe.patches.shared.misc.extension.sharedExtensionPatch

// Uses the plain shared extension (isYouTubeOrYouTubeMusic = false).
// This keeps the .mpe to ~0.2 MB instead of the 28 MB shared-youtube.mpe.
// The GmsCoreSupportPatch loads shared-youtube.mpe separately via extendWith
// only when that patch is applied.
val extensionPatch = sharedExtensionPatch(false, homeActivityInitHook)
