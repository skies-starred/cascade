package foo.starred.cascade.primitives.states

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormat
//~ if >= 26.2 'vertex.VertexFormatElement' -> 'GpuFormat'
import com.mojang.blaze3d.vertex.VertexFormatElement
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import net.minecraft.resources.Identifier
import org.joml.Matrix3x2fc
import kotlin.math.ceil
import kotlin.math.floor

class ArcRenderState(
    val pose: Matrix3x2fc,
    val x: Float,
    val y: Float,
    val radius0: Float,
    val radius1: Float,
    val angle0: Float,
    val angle1: Float,
    val rounded: Boolean,
    val color: Int,
    val scissor: ScreenRectangle? = null
) : GuiElementRenderState {
    private var bounds: ScreenRectangle? = null

    override fun pipeline(): RenderPipeline = PIPELINE
    override fun textureSetup(): TextureSetup = TextureSetup.noTexture()
    override fun scissorArea(): ScreenRectangle? = scissor
    override fun bounds(): ScreenRectangle? = bounds

    init {
        if (radius1 > 0f) {
            val x0 = x - radius1
            val y0 = y - radius1
            val x1 = x + radius1
            val y1 = y + radius1
            bounds = bounds(x0, y0, x1, y1, pose, scissor)
        }
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        if (bounds == null) return

        val r = radius1
        val x0 = x - r
        val y0 = y - r
        val x1 = x + r
        val y1 = y + r

        val r0 = radius0.toInt().toShort().toInt()
        val r1 = (if (rounded) -radius1 else radius1).toInt().toShort().toInt()
        val a0 = (angle0 * 10f).toInt().toShort().toInt()
        val a1 = (angle1 * 10f).toInt().toShort().toInt()

        val u1x = r0 and 0xFFFF
        val u1y = r1 and 0xFFFF
        val u2x = a0 and 0xFFFF
        val u2y = a1 and 0xFFFF

        vertexConsumer.addVertexWith2DPose(pose, x0, y0).setColor(color).setUv(-r, -r).setUv1(u1x, u1y).setUv2(u2x, u2y)
        vertexConsumer.addVertexWith2DPose(pose, x0, y1).setColor(color).setUv(-r, r).setUv1(u1x, u1y).setUv2(u2x, u2y)
        vertexConsumer.addVertexWith2DPose(pose, x1, y1).setColor(color).setUv(r, r).setUv1(u1x, u1y).setUv2(u2x, u2y)
        vertexConsumer.addVertexWith2DPose(pose, x1, y0).setColor(color).setUv(r, -r).setUv1(u1x, u1y).setUv2(u2x, u2y)
    }

    companion object {
        //? if >= 26.2 {
        /*private val VERTEX_FORMAT = VertexFormat.builder(0)
            .addAttribute("Position", GpuFormat.RGB32_FLOAT)
            .addAttribute("Color", GpuFormat.RGBA8_UNORM)
            .addAttribute("UV0", GpuFormat.RG32_FLOAT)
            .addAttribute("UV1", GpuFormat.RG16_SINT)
            .addAttribute("UV2", GpuFormat.RG16_SINT)
            .build()
        *///? } else {
        private val VERTEX_FORMAT = VertexFormat.builder()
            .add("Position", VertexFormatElement.POSITION)
            .add("Color", VertexFormatElement.COLOR)
            .add("UV0", VertexFormatElement.UV0)
            .add("UV1", VertexFormatElement.UV1)
            .add("UV2", VertexFormatElement.UV2)
            .build()
        //? }

        private val PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.GUI_SNIPPET)
                //~ if >= 26.2 'withVertexFormat(VERTEX_FORMAT, VertexFormat.Mode.QUADS)' -> 'withVertexBinding(0, VERTEX_FORMAT)'
                .withVertexFormat(VERTEX_FORMAT, VertexFormat.Mode.QUADS)
                .withLocation(Identifier.fromNamespaceAndPath("cascade", "arc"))
                .withVertexShader(Identifier.fromNamespaceAndPath("cascade", "core/arc"))
                .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/arc"))
                .build()
        )

        private fun bounds(x0: Float, y0: Float, x1: Float, y1: Float, pose: Matrix3x2fc, scissor: ScreenRectangle?): ScreenRectangle {
            val bounds = ScreenRectangle(floor(x0.toDouble()).toInt(), floor(y0.toDouble()).toInt(), ceil((x1 - x0).toDouble()).toInt(), ceil((y1 - y0).toDouble()).toInt()).transformMaxBounds(pose)
            return scissor?.intersection(bounds) ?: bounds
        }
    }
}