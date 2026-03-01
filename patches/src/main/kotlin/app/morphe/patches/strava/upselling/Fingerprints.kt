// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/strava/upselling/Fingerprints.kt
package app.morphe.patches.strava.upselling

import app.morphe.patcher.fingerprint
import com.android.tools.smali.dexlib2.Opcode

internal val getModulesFingerprint = fingerprint {
    opcodes(Opcode.IGET_OBJECT)
    custom { method, classDef ->
        classDef.endsWith("/GenericLayoutEntry;") && method.name == "getModules"
    }
}
