// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/letterboxd/ads/Fingerprints.kt
package app.morphe.patches.letterboxd.ads

import app.morphe.patcher.fingerprint

internal const val admobHelperClassName = "Lcom/letterboxd/letterboxd/helpers/AdmobHelper;"

internal val admobHelperSetShowAdsFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "setShowAds" && classDef.type == admobHelperClassName
    }
}

internal val admobHelperShouldShowAdsFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "shouldShowAds" && classDef.type == admobHelperClassName
    }
}

internal val filmFragmentShowAdsFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "showAds" && classDef.type.endsWith("/FilmFragment;")
    }
}

internal val memberExtensionShowAdsFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "showAds" && classDef.type.endsWith("/AMemberExtensionKt;")
    }
}