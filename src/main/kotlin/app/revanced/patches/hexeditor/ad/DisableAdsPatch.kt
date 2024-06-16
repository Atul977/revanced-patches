package app.revanced.patches.hexeditor.ad

import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val disableAdsPatch = bytecodePatch(
    name = "Disable ads",
) {
    compatibleWith("com.myprog.hexedit")

    val primaryAdsFingerprintResult by primaryAdsFingerprint()

    execute {
        primaryAdsFingerprintResult.mutableMethod.replaceInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """,
        )
    }
}
