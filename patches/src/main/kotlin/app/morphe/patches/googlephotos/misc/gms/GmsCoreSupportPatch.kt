package app.morphe.patches.googlephotos.misc.gms

import app.morphe.patches.googlephotos.misc.extension.extensionPatch
import app.morphe.patches.googlephotos.misc.gms.Constants.PHOTOS_PACKAGE_NAME
import app.morphe.patches.googlephotos.misc.gms.Constants.MORPHE_PHOTOS_PACKAGE_NAME
import app.morphe.patches.googlephotos.misc.features.spoofFeaturesPatch
import app.morphe.patches.shared.misc.gms.gmsCoreSupportPatch
import app.morphe.patches.shared.misc.settings.preference.BasePreferenceScreen

@Suppress("unused")
val gmsCoreSupportPatch = gmsCoreSupportPatch(
    fromPackageName = PHOTOS_PACKAGE_NAME,
    toPackageName = MORPHE_PHOTOS_PACKAGE_NAME,
    mainActivityOnCreateFingerprint = homeActivityOnCreateFingerprint,
    extensionPatch = extensionPatch,
    gmsCoreSupportResourcePatchFactory = ::gmsCoreSupportResourcePatch,
) {
    dependsOn(spoofFeaturesPatch)
    compatibleWith(PHOTOS_PACKAGE_NAME)
}

private fun gmsCoreSupportResourcePatch(
) = app.morphe.patches.shared.misc.gms.gmsCoreSupportResourcePatch(
    fromPackageName = PHOTOS_PACKAGE_NAME,
    toPackageName = MORPHE_PHOTOS_PACKAGE_NAME,
    spoofedPackageSignature = "24bb24c05e47e0aefa68a58a766179d9b613a600",
    screen = GooglePhotosPreferenceScreen.GOOGLE_PHOTOS,
)

private object GooglePhotosPreferenceScreen : BasePreferenceScreen() {
    override fun commit(screen: app.morphe.patches.shared.misc.settings.preference.PreferenceScreenPreference) {
        // No-op: Google Photos has no Morphe settings screen.
    }

    val GOOGLE_PHOTOS = Screen(
        key = "morphe_prefs_google_photos",
    )
}
