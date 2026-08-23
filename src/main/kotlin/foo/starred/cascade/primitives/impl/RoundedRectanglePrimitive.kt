package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.extensions.rectangle.rounded.roundedRectangle
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.base.interfaces.IPrimitiveRounded
import net.minecraft.client.gui.GuiGraphicsExtractor

open class RoundedRectanglePrimitive : IPrimitiveElement<RoundedRectanglePrimitive>(), IPrimitiveRounded {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    override var radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO

    override fun draw(graphics: GuiGraphicsExtractor) {
        graphics.roundedRectangle(x, y, width, height, color, radius)
    }

    companion object {
        val NONE = RoundedRectanglePrimitive()

        inline fun roundedRectangle(block: RoundedRectanglePrimitive.() -> Unit): RoundedRectanglePrimitive {
            return RoundedRectanglePrimitive().apply(block)
        }
    }
}
