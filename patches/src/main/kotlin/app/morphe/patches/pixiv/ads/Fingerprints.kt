package app.morphe.patches.pixiv.ads

import com.android.tools.smali.dexlib2.AccessFlags
import app.morphe.patcher.fingerprint

internal val shouldShowAdsFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("Z")
    custom { methodDef, classDef ->
        classDef.type.endsWith("AdUtils;") && methodDef.name == "shouldShowAds"
    }
}
