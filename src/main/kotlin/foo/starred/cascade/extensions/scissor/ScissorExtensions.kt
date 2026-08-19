package foo.starred.cascade.extensions.scissor

import foo.starred.cascade.Cascade.client
import net.minecraft.client.gui.GuiGraphicsExtractor

inline fun GuiGraphicsExtractor.scissor(x: Number, y: Number, width: Number, height: Number, block: () -> Unit) {
    val x = x.toInt()
    val y = y.toInt()
    val width = width.toInt()
    val height = height.toInt()

    if (width <= 0) return
    if (height <= 0) return

    val x00 = maxOf(0, x)
    val y00 = maxOf(0, y)
    val x01 = minOf(client.window.guiScaledWidth, x + width)
    val y01 = minOf(client.window.guiScaledHeight, y + height)

    if (x01 <= x00) return
    if (y01 <= y00) return

    enableScissor(x00, y00, x01, y01)
    block()
    disableScissor()
}