package foo.starred.cascade.effects.impl

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.graphics.extensions.blur.blur
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

open class BackdropBlurEffect() : IEffect() {
    var blur: Float = 8f
    var color: Int = -1
    var radius: CascadeGeometricRadius? = null

    constructor(block: BackdropBlurEffect.() -> Unit) : this() {
        apply(block)
    }

    override fun before(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {
        if (blur <= 0f) return
        val radius = radius ?: radius(element)

        graphics.blur(element.x, element.y, element.width, element.height, color, radius, blur, pose, scissor)
    }
}
