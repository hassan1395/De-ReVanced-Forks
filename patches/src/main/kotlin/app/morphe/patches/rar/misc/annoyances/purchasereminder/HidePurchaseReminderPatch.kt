package app.morphe.patches.rar.misc.annoyances.purchasereminder

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch

@Suppress("unused")
val hidePurchaseReminderPatch = bytecodePatch(
    name = "Hide purchase reminder",
    description = "Hides the popup that reminds you to purchase the app.",

) {
    compatibleWith("com.rarlab.rar")

    execute {
        showReminderFingerprint.method.addInstruction(0, "return-void")
    }
}
