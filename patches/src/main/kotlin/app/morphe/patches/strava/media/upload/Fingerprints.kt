// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/strava/media/upload/Fingerprints.kt
package app.morphe.patches.strava.media.upload

import app.morphe.patcher.fingerprint

internal val getCompressionQualityFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getCompressionQuality" && classDef.type.endsWith("/MediaUploadParameters;")
    }
}

internal val getMaxDurationFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getMaxDuration" && classDef.type.endsWith("/MediaUploadParameters;")
    }
}

internal val getMaxSizeFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getMaxSize" && classDef.type.endsWith("/MediaUploadParameters;")
    }
}
