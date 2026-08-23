package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.extensions.blur.blur
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.base.interfaces.IPrimitiveRounded
import net.minecraft.client.gui.GuiGraphicsExtractor

open class BlurPrimitive : IPrimitiveElement<BlurPrimitive>(), IPrimitiveRounded {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = 0
    override var radius: CascadeGeometricRadius = CascadeGeometricRadius.ZERO

    var blur: Float = 8f

    override fun draw(graphics: GuiGraphicsExtractor) {
        graphics.blur(x, y, width, height, color, radius, blur)
    }

    companion object {
        val NONE = BlurPrimitive()

        inline fun blur(block: BlurPrimitive.() -> Unit): BlurPrimitive {
            return BlurPrimitive().apply(block)
        }
    }
}
