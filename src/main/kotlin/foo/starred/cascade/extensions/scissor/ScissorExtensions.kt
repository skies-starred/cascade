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

    val x00 = maxOf(0, x)
    val y00 = maxOf(0, y)
    val x01 = minOf(guiWidth(), x + width)
    val y01 = minOf(guiHeight(), y + height)

    if (x01 <= x00) return
    if (y01 <= y00) return

    val screen = ScreenRectangle(x00, y00, x01 - x00, y01 - y00).transformAxisAligned(pose())
    val scissor = scissorStack.peek()

    if (scissor != null) {
        val intersection = scissor.intersection(screen) ?: return
        if (intersection.width() <= 0) return
        if (intersection.height() <= 0) return
    }

    enableScissor(x00, y00, x01, y01)
    block()
    disableScissor()
}