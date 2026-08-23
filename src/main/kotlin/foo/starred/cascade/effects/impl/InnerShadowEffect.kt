package foo.starred.cascade.effects.impl

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.graphics.extensions.shadow.innerShadow
import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

open class InnerShadowEffect() : IEffect() {
    var offset: CascadeGeometricOffset = CascadeGeometricOffset.ZERO
    var blur: Float = 6f
    var color: Int = 0x40000000
    var radius: CascadeGeometricRadius? = null

    constructor(block: InnerShadowEffect.() -> Unit) : this() {
        apply(block)
    }

    override fun after(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {
        if (color ushr 24 == 0) return
        val radius = radius ?: radius(element)

        graphics.innerShadow(element.x, element.y, element.width, element.height, offset, blur, color, radius, pose, scissor)
    }
}
