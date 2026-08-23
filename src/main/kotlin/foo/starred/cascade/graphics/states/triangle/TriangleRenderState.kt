package foo.starred.cascade.graphics.states.triangle

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormat
//~ if >= 26.2 'vertex.VertexFormatElement' -> 'GpuFormat'
import com.mojang.blaze3d.vertex.VertexFormatElement
import foo.starred.cascade.utils.bounds
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
//~ if >= 26.1 'gui.render.state.GuiElementRenderState' -> 'renderer.state.gui.GuiElementRenderState'
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import net.minecraft.resources.Identifier
import org.joml.Matrix3x2fc
import kotlin.math.max
import kotlin.math.min

class TriangleRenderState(
    val pose: Matrix3x2fc,
    val x0: Float,
    val y0: Float,
    val x1: Float,
    val y1: Float,
    val x2: Float,
    val y2: Float,
    val color: Int,
    val scissor: ScreenRectangle? = null
) : GuiElementRenderState {
    private val x00 = min(x0, min(x1, x2))
    private val y00 = min(y0, min(y1, y2))
    private val x01 = max(x0, max(x1, x2))
    private val y01 = max(y0, max(y1, y2))

    override fun pipeline(): RenderPipeline = PIPELINE
    override fun textureSetup(): TextureSetup = TextureSetup.noTexture()
    override fun scissorArea(): ScreenRectangle? = scissor
    override fun bounds(): ScreenRectangle? = bounds(x00, y00, x01, y01, pose, scissor)

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        val p1x = (x1 - x0).toInt().toShort().toInt() and 0xFFFF
        val p1y = (y1 - y0).toInt().toShort().toInt() and 0xFFFF
        val p2x = (x2 - x0).toInt().toShort().toInt() and 0xFFFF
        val p2y = (y2 - y0).toInt().toShort().toInt() and 0xFFFF

        fun vertex(x: Float, y: Float) {
            vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(color).setUv(x - x0, y - y0).setUv1(p1x, p1y).setUv2(p2x, p2y)
        }

        vertex(x00, y00)
        vertex(x00, y01)
        vertex(x01, y01)
        vertex(x01, y00)
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
                .withLocation(Identifier.fromNamespaceAndPath("cascade", "triangle"))
                .withVertexShader(Identifier.fromNamespaceAndPath("cascade", "core/shapes/triangle/triangle"))
                .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/shapes/triangle/triangle"))
                .build()
        )
    }
}
