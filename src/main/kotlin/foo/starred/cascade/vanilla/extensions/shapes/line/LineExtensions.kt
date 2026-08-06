package foo.starred.cascade.vanilla.extensions.shapes.line

import net.minecraft.client.gui.GuiGraphicsExtractor
import kotlin.math.atan2
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

@JvmOverloads
fun GuiGraphicsExtractor.line(x1: Int, y1: Int, x2: Int, y2: Int, color: Int, thickness: Int = 1) {
    val dx = x2 - x1
    val dy = y2 - y1
    val length = sqrt((dx * dx + dy * dy).toDouble())
    if (length <= 0f) return

    val pose = pose()
    pose.pushMatrix()

    pose.translate(x1.toFloat(), y1.toFloat())
    pose.rotate(atan2(dy.toFloat(), dx.toFloat()))

    if (thickness == 1) {
        fill(0, 0, length.toInt(), 1, color)
        pose.popMatrix()
        return
    }

    val half = thickness / 2f
    fill(0, floor(-half).toInt(), length.toInt(), ceil(half).toInt(), color)

    pose.popMatrix()
}