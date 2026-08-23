package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.extensions.rectangle.solid.rectangle
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor

open class RectanglePrimitive : IPrimitiveElement<RectanglePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    override fun draw(graphics: GuiGraphicsExtractor) {
        graphics.rectangle(x, y, width, height, color)
    }

    companion object {
        val NONE = RectanglePrimitive()

        inline fun rectangle(block: RectanglePrimitive.() -> Unit): RectanglePrimitive {
            return RectanglePrimitive().apply(block)
        }
    }
}
