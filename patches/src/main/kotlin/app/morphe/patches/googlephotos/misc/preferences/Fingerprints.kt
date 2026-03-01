// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlephotos/misc/preferences/Fingerprints.kt
package app.morphe.patches.googlephotos.misc.preferences

import app.morphe.patcher.fingerprint

internal val backupPreferencesFingerprint = fingerprint {
    returns("Lcom/google/android/apps/photos/backup/data/BackupPreferences;")
    strings("backup_prefs_had_backup_only_when_charging_enabled")
}
