package foo.starred.cascade.effects.impl

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.graphics.extensions.shadow.dropShadow
import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

open class OuterGlowEffect() : IEffect() {
    var blur: Float = 12f
    var spread: Float = 0f
    var color: Int = 0x6600AAFF
    var radius: CascadeGeometricRadius? = null

    constructor(block: OuterGlowEffect.() -> Unit) : this() {
        apply(block)
    }

    override fun before(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {
        if (color ushr 24 == 0) return
        val radius = radius ?: radius(element)

        graphics.dropShadow(element.x, element.y, element.width, element.height, CascadeGeometricOffset.ZERO, blur, spread, color, radius, pose, scissor)
    }
}
