// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlenews/misc/extension/ExtensionPatch.kt
package app.morphe.patches.googlenews.misc.extension

import app.morphe.patches.googlenews.misc.extension.hooks.startActivityInitHook
import app.morphe.patches.shared.misc.extension.sharedExtensionPatch

val extensionPatch = sharedExtensionPatch(false, startActivityInitHook)
