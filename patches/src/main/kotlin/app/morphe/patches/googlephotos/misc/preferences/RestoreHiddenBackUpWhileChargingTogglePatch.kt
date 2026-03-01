// Forked from: https://github.com/ReVanced/revanced-patches/blob/6b06b9d1328b971a06d10b4247f4c10f050e4f61/patches/src/main/kotlin/app/revanced/patches/googlephotos/misc/preferences/RestoreHiddenBackUpWhileChargingTogglePatch.kt
package app.morphe.patches.googlephotos.misc.preferences

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.indexOfFirstInstructionOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

@Deprecated("This patch no longer works and this code will soon be deleted")
@Suppress("unused")
val restoreHiddenBackUpWhileChargingTogglePatch = bytecodePatch(
    description = "Restores a hidden toggle to only run backups when the device is charging."
) {
    compatibleWith("com.google.android.apps.photos"("7.11.0.705590205"))

    execute {
        // Patches 'backup_prefs_had_backup_only_when_charging_enabled' to always be true.
        backupPreferencesFingerprint.let {
            it.method.apply {
                val index = indexOfFirstInstructionOrThrow(
                    it.stringMatches!!.first().index,
                    Opcode.MOVE_RESULT
                )
                val register = getInstruction<OneRegisterInstruction>(index).registerA
                addInstruction(index + 1, "const/4 v$register, 0x1")
            }
        }
    }
}
