package foo.starred.cascade.primitives.impl

import foo.starred.cascade.events.impl.MouseEvent
import foo.starred.cascade.graphics.extensions.scissor.scissor
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.base.interfaces.IPrimitiveScrollable
import net.minecraft.client.gui.GuiGraphicsExtractor

open class ScrollablePrimitive : IPrimitiveElement<ScrollablePrimitive>(), IPrimitiveScrollable {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: Int = -1

    val content: Int
        get() = children.maxOfOrNull { (it.y - y) + it.height }?.toInt() ?: 0

    val maxScroll: Int
        get() = (content - height).coerceAtLeast(0f).toInt()

    var scroll: Int = 0
        private set

    init {
        on<MouseEvent.Scroll> {
            scroll = (scroll - amount.toInt() * 10).coerceIn(0, maxScroll)
            cancel()
        }
    }

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        graphics.scissor(x, y, width, height) {
            graphics.pose().pushMatrix()
            graphics.pose().translate(0f, -scroll.toFloat())

            super.render(graphics)

            graphics.pose().popMatrix()
        }
    }

    override fun layout() {
        super.layout()
        scroll = scroll.coerceIn(0, maxScroll)
    }

    override fun find(x: Double, y: Double): IPrimitiveElement<*>? {
        if (!contains(x, y)) return null

        val oy = y + scroll
        for (c in children.asReversed()) {
            val a = c.find(x, oy) ?: continue
            return a
        }

        return this
    }

    companion object {
        val NONE = ScrollablePrimitive()

        inline fun scrollable(block: ScrollablePrimitive.() -> Unit): ScrollablePrimitive {
            return ScrollablePrimitive().apply(block)
        }
    }
}
