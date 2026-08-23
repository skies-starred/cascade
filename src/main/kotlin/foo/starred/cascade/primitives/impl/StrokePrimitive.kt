package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.extensions.stroke.stroke
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import kotlin.math.abs

open class StrokePrimitive : IPrimitiveElement<StrokePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var color: Int = -1

    override var width: Float
        get() = abs(x2 - x)
        set(value) {
            x2 = x + value
        }

    override var height: Float
        get() = abs(y2 - y)
        set(value) {
            y2 = y + value
        }

    var x2: Float = 0f
    var y2: Float = 0f

    var thickness: Float = 1f

    override fun draw(graphics: GuiGraphicsExtractor) {
        graphics.stroke(x, y, x2, y2, color, thickness)
    }

    companion object {
        val NONE = StrokePrimitive()

        inline fun line(block: StrokePrimitive.() -> Unit): StrokePrimitive {
            return StrokePrimitive().apply(block)
        }
    }
}
