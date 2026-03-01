// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/strava/upselling/DisableSubscriptionSuggestionsPatch.kt
package app.morphe.patches.strava.upselling

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.strava.distractions.hideDistractionsPatch

@Suppress("unused")
@Deprecated("Superseded by \"Hide distractions\" patch", ReplaceWith("hideDistractionsPatch"))
val disableSubscriptionSuggestionsPatch = bytecodePatch(
    name = "Disable subscription suggestions",
) {
    compatibleWith("com.strava")

    dependsOn(hideDistractionsPatch.apply {
        options["upselling"] = true
        options["promo"] = false
        options["followSuggestions"] = false
        options["challengeSuggestions"] = false
        options["joinChallenge"] = false
        options["joinClub"] = false
        options["activityLookback"] = false
    })
}
