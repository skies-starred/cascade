@file:Suppress("Unused")

package foo.starred.cascade.primitives.states

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormat
//~ if >= 26.2 'vertex.VertexFormatElement' -> 'GpuFormat'
import com.mojang.blaze3d.vertex.VertexFormatElement
import foo.starred.cascade.primitives.data.roundedrectangle.RoundedRectangleRadius
import foo.starred.cascade.utils.submit
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import net.minecraft.resources.Identifier
import org.joml.Matrix3x2f
import org.joml.Matrix3x2fc
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min

class RoundedRectangleRenderState(
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val color: Int,
    val radius: RoundedRectangleRadius,
    val outline: Float = 0f,
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
        val line = min(outline, 127f) / 127f

        fun vertex(x: Float, y: Float, u: Float, v: Float) {
            vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color).setUv(u, v).setUv1(width, height).setUv2(u2x, u2y).setNormal(line, 0f, 0f)
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
                .withLocation(Identifier.fromNamespaceAndPath("cascade", "rounded_rect"))
                .withVertexShader(Identifier.fromNamespaceAndPath("cascade", "core/rounded_rect"))
                .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/rounded_rect"))
                .build()
        )

        fun extract(graphics: GuiGraphicsExtractor, x: Float, y: Float, width: Float, height: Float, color: Int, radius: RoundedRectangleRadius, outline: Float = 0f, pose: Matrix3x2f? = null, scissor: ScreenRectangle? = null, bounds: ScreenRectangle? = null) {
            val x1 = x + width
            val y1 = y + height
            val pose = pose ?: Matrix3x2f(graphics.pose())
            val bounds = bounds ?: bounds(x, y, x1, y1, pose, scissor)

            RoundedRectangleRenderState(pose, x, y, x1, y1, color, radius, outline, scissor, bounds).submit(graphics)
        }

        private fun bounds(x0: Float, y0: Float, x1: Float, y1: Float, pose: Matrix3x2fc, scissor: ScreenRectangle?): ScreenRectangle? {
            val bounds = ScreenRectangle(floor(x0.toDouble()).toInt(), floor(y0.toDouble()).toInt(), ceil((x1 - x0).toDouble()).toInt(), ceil((y1 - y0).toDouble()).toInt()).transformMaxBounds(pose)
            return if (scissor != null) scissor.intersection(bounds) else bounds
        }
    }
}