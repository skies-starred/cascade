package foo.starred.cascade.screen

import foo.starred.cascade.Cascade
import net.minecraft.client.gui.GuiGraphicsExtractor
import kotlin.math.min

object Resolution {
    private const val REFERENCE_WIDTH = 960f
    private const val REFERENCE_HEIGHT = 540f

    var scale = 1f
        private set

    var width = 960f
        private set

    var height = 540f
        private set

    internal fun refresh() {
        val guiWidth = Cascade.client.window.guiScaledWidth
        val guiHeight = Cascade.client.window.guiScaledHeight

        scale = min(guiWidth / REFERENCE_WIDTH, guiHeight / REFERENCE_HEIGHT)

        width = guiWidth / scale
        height = guiHeight / scale
    }

    fun push(ctx: GuiGraphicsExtractor) {
        refresh()
        ctx.pose().pushMatrix()
        ctx.pose().scale(scale, scale)
    }

    fun pop(ctx: GuiGraphicsExtractor) {
        ctx.pose().popMatrix()
    }
}