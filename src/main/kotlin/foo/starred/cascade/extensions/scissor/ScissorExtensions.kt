package foo.starred.cascade.extensions.scissor

import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle

inline fun GuiGraphicsExtractor.scissor(x: Number, y: Number, width: Number, height: Number, block: () -> Unit) {
    val x = x.toInt()
    val y = y.toInt()
    val width = width.toInt()
    val height = height.toInt()

    if (width <= 0) return
    if (height <= 0) return

    val screen = ScreenRectangle(x, y, width, height).transformAxisAligned(pose())
    val scissor = scissorStack.peek()

    if (scissor != null) {
        val intersection = scissor.intersection(screen) ?: return
        if (intersection.width() <= 0) return
        if (intersection.height() <= 0) return
    }

    enableScissor(x, y, x + width, y + height)
    block()
    disableScissor()
}