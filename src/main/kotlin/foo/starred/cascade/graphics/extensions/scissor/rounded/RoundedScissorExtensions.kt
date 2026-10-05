package foo.starred.cascade.graphics.extensions.scissor.rounded

import foo.starred.cascade.graphics.extensions.scissor.scissor
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState.Companion.bounds
import foo.starred.cascade.graphics.states.impl.scissor.rounded.blit.RoundedScissorBlitRenderState
import foo.starred.cascade.graphics.states.impl.scissor.rounded.start.RoundedScissorStartRenderState
import net.minecraft.client.gui.GuiGraphicsExtractor
import org.joml.Matrix3x2f

fun GuiGraphicsExtractor.roundedScissor(x: Number, y: Number, width: Number, height: Number, radius: CascadeGeometricRadius, color: CascadeGeometricColor = CascadeGeometricColor.WHITE, block: () -> Unit) {
    val x0 = x.toFloat()
    val y0 = y.toFloat()
    val width = width.toFloat()
    val height = height.toFloat()

    val x1 = x0 + width
    val y1 = y0 + height
    val pose = Matrix3x2f(pose())
    val scissor = scissorStack.peek()

    val bounds = bounds(x0, y0, x1, y1, pose, scissor) ?: return
    if (bounds.width <= 0) return
    if (bounds.height <= 0) return

    nextStratum()
    RoundedScissorStartRenderState(bounds).submit(this)

    nextStratum()
    scissor(x0, y0, width, height) {
        block()
    }

    nextStratum()
    RoundedScissorBlitRenderState(pose, x0, y0, x1, y1, color, radius, scissor, bounds).submit(this)
    nextStratum()
}
