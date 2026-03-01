// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlephotos/misc/extension/Hooks.kt
package app.morphe.patches.googlephotos.misc.extension

import app.morphe.patches.shared.misc.extension.ExtensionHook
import app.morphe.patches.shared.misc.extension.activityOnCreateExtensionHook

/**
 * Extension hook for Google Photos HomeActivity.
 * Uses activityOnCreateExtensionHook which hooks into onCreate
 * and passes the Activity as the context.
 */
internal val homeActivityInitHook = activityOnCreateExtensionHook("/HomeActivity;")
