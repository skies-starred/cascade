package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.extensions.circle.circle
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.base.interfaces.IPrimitiveRounded
import net.minecraft.client.gui.GuiGraphicsExtractor
import kotlin.math.max

open class CirclePrimitive : IPrimitiveElement<CirclePrimitive>(), IPrimitiveRounded {
    override var x: Float = 0f
    override var y: Float = 0f
    override var color: Int = -1
    override var radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO // Only takes top left radius into account!

    override var width: Float
        get() = radius.tl * 2f
        set(value) {
            radius = CascadeGeometricRadius(max(value / 2f, radius.tl))
        }

    override var height: Float
        get() = radius.tl * 2f
        set(value) {
            radius = CascadeGeometricRadius(max(value / 2f, radius.tl))
        }

    override fun draw(graphics: GuiGraphicsExtractor) {
        val radius = radius.tl
        graphics.circle(x + radius, y + radius, radius, color)
    }

    companion object {
        val NONE = CirclePrimitive()

        inline fun circle(block: CirclePrimitive.() -> Unit): CirclePrimitive {
            return CirclePrimitive().apply(block)
        }
    }
}
