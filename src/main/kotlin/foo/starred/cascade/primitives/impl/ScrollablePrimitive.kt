package foo.starred.cascade.primitives.impl

import foo.starred.cascade.events.impl.MouseEvent
import foo.starred.cascade.graphics.extensions.scissor.scissor
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.base.interfaces.IPrimitiveScrollable
import net.minecraft.client.gui.GuiGraphicsExtractor
import org.joml.Matrix3x2f

open class ScrollablePrimitive : IPrimitiveElement<ScrollablePrimitive>(), IPrimitiveScrollable {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 0f
    override var height: Float = 0f
    override var color: CascadeGeometricColor = CascadeGeometricColor.WHITE

    val content: Int
        get() = children.maxOfOrNull { (it.y - y) + it.height }?.toInt() ?: 0

    val maxScroll: Int
        get() = (content - height).coerceAtLeast(0f).toInt()

    var scroll: Int = 0
        private set

    var overscan: Float = 20f
    var virtual: Boolean = true

    init {
        on<MouseEvent.Scroll> {
            val last = scroll
            scroll = (scroll - amount.toInt() * 10).coerceIn(0, maxScroll)

            if (scroll != last) {
                root.mouseMove(x, y)
            }

            cancel()
        }
    }

    override fun render(graphics: GuiGraphicsExtractor) {
        if (!visible) return

        graphics.scissor(x, y, width, height) {
            graphics.pose().pushMatrix()
            graphics.pose().translate(0f, -scroll.toFloat())

            render0(graphics)
            render1(graphics)

            graphics.pose().popMatrix()
        }
    }

    override fun layout() {
        super.layout()
        scroll = scroll.coerceIn(0, maxScroll)
    }

    override fun find(x: Double, y: Double): IPrimitiveElement<*>? {
        if (!contains(x, y)) return null

        val y2 = y + scroll
        if (!virtual) {
            for (c in children.asReversed()) return c.find(x, y2) ?: continue
            return this
        }

        val y0 = this.y + scroll
        val y1 = y0 + height
        for (c in children.asReversed()) {
            if (c.y + c.height < y0) continue
            if (c.y > y1) continue

            return c.find(x, y2) ?: continue
        }

        return this
    }

    private fun render0(graphics: GuiGraphicsExtractor) {
        if (effects.isEmpty()) {
            draw(graphics)
            return
        }

        val pose = Matrix3x2f(graphics.pose())
        val scissor = graphics.scissorStack.peek()

        for (e in effects) {
            e.before(self, graphics, pose, scissor)
        }

        draw(graphics)

        for (e in effects) {
            e.after(self, graphics, pose, scissor)
        }
    }

    private fun render1(graphics: GuiGraphicsExtractor) {
        if (!virtual) {
            for (c in children) c.render(graphics)
            return
        }

        val y0 = y + scroll - overscan
        val y1 = y + scroll + height + overscan
        for (c in children) {
            if (c.y + c.height < y0) continue
            if (c.y > y1) continue

            c.render(graphics)
        }
    }

    companion object {
        val NONE = ScrollablePrimitive()

        inline fun scrollable(block: ScrollablePrimitive.() -> Unit): ScrollablePrimitive {
            return ScrollablePrimitive().apply(block)
        }
    }
}
