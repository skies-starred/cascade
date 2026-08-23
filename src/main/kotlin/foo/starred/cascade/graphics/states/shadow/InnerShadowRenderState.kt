@file:Suppress("Unused")

package foo.starred.cascade.states.impl.shadow

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormat
//~ if >= 26.2 'vertex.VertexFormatElement' -> 'GpuFormat'
import com.mojang.blaze3d.vertex.VertexFormatElement
import foo.starred.cascade.geometry.CascadeGeometricOffset
import foo.starred.cascade.geometry.CascadeGeometricRadius
import foo.starred.cascade.utils.bounds
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import net.minecraft.resources.Identifier
import org.joml.Matrix3x2fc
import kotlin.math.min

class InnerShadowRenderState(
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val offset: CascadeGeometricOffset,
    val blur: Float,
    val color: Int,
    val radius: CascadeGeometricRadius,
    val scissor: ScreenRectangle? = null,
    val bounds: ScreenRectangle? = bounds(x0, y0, x1, y1, pose, scissor)
) : GuiElementRenderState {
    override fun pipeline(): RenderPipeline = PIPELINE
    override fun textureSetup(): TextureSetup = TextureSetup.noTexture()
    override fun scissorArea(): ScreenRectangle? = scissor
    override fun bounds(): ScreenRectangle? = bounds

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        val width = (x1 - x0).toInt()
        val height = (y1 - y0).toInt()

        val tl = (min(radius.tl, 25.5f) * 10f).toInt()
        val tr = (min(radius.tr, 25.5f) * 10f).toInt()
        val bl = (min(radius.bl, 25.5f) * 10f).toInt()
        val br = (min(radius.br, 25.5f) * 10f).toInt()

        val u2x = (tr shl 8) or tl
        val u2y = (bl shl 8) or br

        val x2 = offset.x.coerceIn(-127f, 127f) / 127f
        val y2 = offset.y.coerceIn(-127f, 127f) / 127f
        val blur = min(blur, 127f) / 127f

        fun vertex(x: Float, y: Float, u: Float, v: Float) {
            vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color).setUv(u, v).setUv1(width, height).setUv2(u2x, u2y).setNormal(x2, blur, y2)
        }

        vertex(x0, y0, 0f, 0f)
        vertex(x0, y1, 0f, height.toFloat())
        vertex(x1, y1, width.toFloat(), height.toFloat())
        vertex(x1, y0, width.toFloat(), 0f)
    }

    companion object {
        //? if >= 26.2 {
        /*private val VERTEX_FORMAT = VertexFormat.builder(0)
            .addAttribute("Position", GpuFormat.RGB32_FLOAT)
            .addAttribute("Color", GpuFormat.RGBA8_UNORM)
            .addAttribute("UV0", GpuFormat.RG32_FLOAT)
            .addAttribute("UV1", GpuFormat.RG16_SINT)
            .addAttribute("UV2", GpuFormat.RG16_SINT)
            .addAttribute("Normal", GpuFormat.RGBA8_SNORM)
            .build()
        *///? } else {
        private val VERTEX_FORMAT = VertexFormat.builder()
            .add("Position", VertexFormatElement.POSITION)
            .add("Color", VertexFormatElement.COLOR)
            .add("UV0", VertexFormatElement.UV0)
            .add("UV1", VertexFormatElement.UV1)
            .add("UV2", VertexFormatElement.UV2)
            .add("Normal", VertexFormatElement.NORMAL)
            .padding(1)
            .build()
        //? }

        private val PIPELINE: RenderPipeline = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.GUI_SNIPPET)
                //~ if >= 26.2 'withVertexFormat(VERTEX_FORMAT, VertexFormat.Mode.QUADS)' -> 'withVertexBinding(0, VERTEX_FORMAT)'
                .withVertexFormat(VERTEX_FORMAT, VertexFormat.Mode.QUADS)
                .withLocation(Identifier.fromNamespaceAndPath("cascade", "inner_shadow"))
                .withVertexShader(Identifier.fromNamespaceAndPath("cascade", "core/effects/shadow/inner/inner_shadow"))
                .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/effects/shadow/inner/inner_shadow"))
                .build()
        )
    }
}
