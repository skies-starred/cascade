package foo.starred.cascade.effects.impl

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.graphics.extensions.rectangle.hollow.hollowRectangle
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

open class OutlineEffect() : IEffect() {
    var width: Float = 1f
    var color: Int = -1
    var inset: Boolean = true
    var radius: CascadeGeometricRadius? = null

    constructor(block: OutlineEffect.() -> Unit) : this() {
        apply(block)
    }

    override fun after(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {
        if (color ushr 24 == 0) return
        if (width <= 0f) return
        val radius = radius ?: radius(element)

        val i0 = if (inset) 0f else -width
        val i1 = if (inset) 0f else width

        graphics.hollowRectangle(element.x + i0, element.y + i0, element.width - i0 * 2, element.height - i0 * 2, width, color, radius + i1, pose, scissor)
    }
}
