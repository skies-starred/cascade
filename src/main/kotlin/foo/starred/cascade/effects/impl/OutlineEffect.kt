package foo.starred.cascade.effects.impl

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.graphics.extensions.rectangle.hollow.hollowRectangle
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

open class OutlineEffect() : IEffect() {
    var width: Float = 1f
    var color: CascadeGeometricColor = CascadeGeometricColor.WHITE
    var inset: Boolean = true
    var radius: CascadeGeometricRadius? = null

    constructor(block: OutlineEffect.() -> Unit) : this() {
        apply(block)
    }

    override fun after(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {
        if (width <= 0f) return
        val radius = radius ?: radius(element)

        graphics.hollowRectangle(element.x, element.y, element.width, element.height, width, color, radius, inset, pose, scissor)
    }
}
