package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.extensions.triangle.triangle
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import org.joml.Vector2f
import kotlin.math.max
import kotlin.math.min

open class TrianglePrimitive : IPrimitiveElement<TrianglePrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var color: Int = -1

    override var width: Float
        get() = max(p0.x, max(p1.x, p2.x)) - min(p0.x, min(p1.x, p2.x))
        set(value) {
            val width = width
            if (width <= 0f) return
            if (value <= 0f) return

            val x0 = min(p0.x, min(p1.x, p2.x))
            val i0 = value / width

            p0.x = (p0.x - x0) * i0
            p1.x = (p1.x - x0) * i0
            p2.x = (p2.x - x0) * i0
        }

    override var height: Float
        get() = max(p0.y, max(p1.y, p2.y)) - min(p0.y, min(p1.y, p2.y))
        set(value) {
            val height = height
            if (height <= 0f) return
            if (value <= 0f) return

            val y0 = min(p0.y, min(p1.y, p2.y))
            val i0 = value / height

            p0.y = (p0.y - y0) * i0
            p1.y = (p1.y - y0) * i0
            p2.y = (p2.y - y0) * i0
        }

    var p0: Vector2f = Vector2f()
    var p1: Vector2f = Vector2f()
    var p2: Vector2f = Vector2f()

    override fun draw(graphics: GuiGraphicsExtractor) {
        graphics.triangle(x + p0.x, y + p0.y, x + p1.x, y + p1.y, x + p2.x, y + p2.y, color)
    }

    companion object {
        val NONE = TrianglePrimitive()

        inline fun triangle(block: TrianglePrimitive.() -> Unit): TrianglePrimitive {
            return TrianglePrimitive().apply(block)
        }
    }
}
