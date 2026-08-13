package foo.starred.cascade.primitives.impl

import foo.starred.cascade.extensions.circle.circle
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import kotlin.math.max

open class CirclePrimitive : IPrimitiveElement<CirclePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var color: Int = -1

    override var width: Float
        get() = radius * 2f
        set(value) {
            radius = max(value / 2f, radius)
        }

    override var height: Float
        get() = radius * 2f
        set(value) {
            radius = max(value / 2f, radius)
        }

    var radius: Float = 0f

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        graphics.circle(x + radius, y + radius, radius, color)
        super.render(graphics)
    }

    companion object {
        val NONE = CirclePrimitive()

        inline fun circle(block: CirclePrimitive.() -> Unit): CirclePrimitive {
            return CirclePrimitive().apply(block)
        }
    }
}