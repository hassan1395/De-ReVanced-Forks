// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/strava/misc/extension/Hooks.kt
package app.morphe.patches.strava.misc.extension

import app.morphe.patches.shared.misc.extension.extensionHook

internal val applicationOnCreateHook = extensionHook {
    custom { method, classDef ->
        method.name == "onCreate" && classDef.type.endsWith("/StravaApplication;")
    }
}
