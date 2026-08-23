package foo.starred.cascade.primitives.impl

import foo.starred.cascade.graphics.extensions.arc.arc
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import kotlin.math.max

open class ArcPrimitive : IPrimitiveElement<ArcPrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var color: Int = -1

    override var width: Float
        get() = radius1 * 2f
        set(value) {
            radius1 = max(value / 2f, radius1)
        }

    override var height: Float
        get() = radius1 * 2f
        set(value) {
            radius1 = max(value / 2f, radius1)
        }

    var radius0: Float = 0f
    var radius1: Float = 0f
    var angle0: Float = 0f
    var angle1: Float = 360f
    var rounded: Boolean = false

    override fun draw(graphics: GuiGraphicsExtractor) {
        graphics.arc(x + radius1, y + radius1, radius0, radius1, angle0, angle1, rounded, color)
    }

    companion object {
        val NONE = ArcPrimitive()

        inline fun arc(block: ArcPrimitive.() -> Unit): ArcPrimitive {
            return ArcPrimitive().apply(block)
        }
    }
}
