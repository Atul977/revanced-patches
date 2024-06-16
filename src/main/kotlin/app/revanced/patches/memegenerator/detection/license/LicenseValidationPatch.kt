package app.revanced.patches.memegenerator.detection.license

import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val licenseValidationPatch = bytecodePatch(
    description = "Disables Firebase license validation.",
) {
    val licenseValidationFingerprintResult by licenseValidationFingerprint()

    execute {
        licenseValidationFingerprintResult.mutableMethod.replaceInstructions(
            0,
            """
                const/4 p0, 0x1
                return  p0
            """,
        )
    }
}
