package foo.starred.cascade.primitives.impl

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.vanilla.extensions.shapes.rectangle.outline
import foo.starred.cascade.vanilla.extensions.shapes.rectangle.rectangle
import net.minecraft.client.gui.GuiGraphicsExtractor

open class RectanglePrimitive : IPrimitiveElement<RectanglePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    var border: Boolean = false
    var borderInset: Boolean = true
    var borderWidth: Float = 1f
    var borderColor: Int = -1

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        val x = x.toInt()
        val y = y.toInt()
        val width = width.toInt()
        val height = height.toInt()
        val borderWidth = borderWidth.toInt()

        graphics.rectangle(x, y, width, height, color)
        if (border) graphics.outline(x, y, width, height, borderWidth, borderColor, borderInset)

        super.render(graphics)
    }

    companion object {
        val NONE = RectanglePrimitive()

        inline fun rectangle(block: RectanglePrimitive.() -> Unit): RectanglePrimitive {
            return RectanglePrimitive().apply(block)
        }
    }
}