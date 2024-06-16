package app.revanced.patches.myfitnesspal.ads

import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val hideAdsPatch = bytecodePatch(
    name = "Hide ads",
    description = "Hides most of the ads across the app.",
) {
    compatibleWith("com.myfitnesspal.android")

    val isPremiumUseCaseImplFingerprintResult by isPremiumUseCaseImplFingerprint()
    val mainActivityNavigateToNativePremiumUpsellFingerprintResult by mainActivityNavigateToNativePremiumUpsellFingerprint()

    execute {
        // Overwrite the premium status specifically for ads.
        isPremiumUseCaseImplFingerprintResult.mutableMethod.replaceInstructions(
            0,
            """
                sget-object v0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;
                return-object v0
            """,
        )

        // Prevent the premium upsell dialog from showing when the main activity is launched.
        // In other places that are premium-only the dialog will still show.
        mainActivityNavigateToNativePremiumUpsellFingerprintResult.mutableMethod.replaceInstructions(
            0,
            "return-void",
        )
    }
}
