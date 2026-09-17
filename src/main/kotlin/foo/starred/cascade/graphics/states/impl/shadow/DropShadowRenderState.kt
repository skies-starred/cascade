@file:Suppress("Unused")

package foo.starred.cascade.graphics.states.impl.shadow

import com.mojang.blaze3d.vertex.VertexConsumer
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import foo.starred.cascade.graphics.states.base.CascadeGuiElementRenderState
import foo.starred.cascade.graphics.states.base.IRoundedGuiElementRenderState
import foo.starred.cascade.graphics.states.pipeline.render.builder.CascadeRenderPipelineBuilder.Companion.cascadeRenderPipeline
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.gui.navigation.ScreenRectangle
import org.joml.Matrix3x2fc
import kotlin.math.min

class DropShadowRenderState(
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val width: Float,
    val height: Float,
    val color: CascadeGeometricColor,
    val radius: CascadeGeometricRadius,
    val blur: Float = 0f,
    val scissor: ScreenRectangle? = null,
    val bounds: ScreenRectangle? = bounds(x0, y0, x1, y1, pose, scissor)
) : CascadeGuiElementRenderState(PIPELINE, scissor, bounds), IRoundedGuiElementRenderState {
    override fun buildVertices(vertexConsumer: VertexConsumer) {
        val blur0 = min(blur, 127f) / 127f

        vertexConsumer.quad(pose, x0, y0, x1, y1, color, radius) { _, _ ->
            setNormal(0f, blur0, 0f)
        }
    }

    companion object {
        val PIPELINE = cascadeRenderPipeline("drop_shadow", CascadeVertexFormats.UV3_NORMAL, "core/effects/shadow/drop/drop_shadow")
    }
}
