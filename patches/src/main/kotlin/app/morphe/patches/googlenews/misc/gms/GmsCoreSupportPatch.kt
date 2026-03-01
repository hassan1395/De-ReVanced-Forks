// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlenews/misc/gms/GmsCoreSupportPatch.kt
package app.morphe.patches.googlenews.misc.gms

import app.morphe.patches.googlenews.misc.extension.extensionPatch
import app.morphe.patches.googlenews.misc.gms.Constants.MAGAZINES_PACKAGE_NAME
import app.morphe.patches.googlenews.misc.gms.Constants.REVANCED_MAGAZINES_PACKAGE_NAME
import app.morphe.patches.shared.misc.gms.gmsCoreSupportPatch
import app.morphe.patches.shared.misc.gms.gmsCoreSupportResourcePatch
import app.morphe.patches.shared.misc.settings.preference.BasePreferenceScreen

@Suppress("unused")
val gmsCoreSupportPatch = gmsCoreSupportPatch(
    fromPackageName = MAGAZINES_PACKAGE_NAME,
    toPackageName = REVANCED_MAGAZINES_PACKAGE_NAME,
    mainActivityOnCreateFingerprint = magazinesActivityOnCreateFingerprint,
    extensionPatch = extensionPatch,
    gmsCoreSupportResourcePatchFactory = ::gmsCoreSupportResourcePatch,
) {
    // Remove version constraint,
    // once https://github.com/ReVanced/revanced-patches/pull/3111#issuecomment-2240877277 is resolved.
    compatibleWith(MAGAZINES_PACKAGE_NAME("5.108.0.644447823"))
}

private fun gmsCoreSupportResourcePatch(
) = app.morphe.patches.shared.misc.gms.gmsCoreSupportResourcePatch(
    fromPackageName = MAGAZINES_PACKAGE_NAME,
    toPackageName = REVANCED_MAGAZINES_PACKAGE_NAME,
    spoofedPackageSignature = "24bb24c05e47e0aefa68a58a766179d9b613a666",
    screen = GoogleNewsPreferenceScreen.GOOGLE_NEWS,
)

private object GoogleNewsPreferenceScreen : BasePreferenceScreen() {
    override fun commit(
        screen: app.morphe.patches.shared.misc.settings.preference.PreferenceScreenPreference,
    ) {
        // No-op: Google News has no Morphe settings screen.
    }

    val GOOGLE_NEWS = Screen(
        key = "morphe_prefs_google_news",
    )
}
