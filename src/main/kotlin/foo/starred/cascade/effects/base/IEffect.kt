package foo.starred.cascade.effects.base

import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.base.interfaces.IPrimitiveRounded
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2f

abstract class IEffect {
    var element: IPrimitiveElement<*>? = null

    open fun before(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {}
    open fun after(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {}

    fun radius(element: IPrimitiveElement<*>): CascadeGeometricRadius {
        return (element as? IPrimitiveRounded)?.radius ?: CascadeGeometricRadius.ZERO
    }
}
