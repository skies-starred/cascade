package foo.starred.cascade.graphics.states.stroke

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
import kotlin.math.sqrt

class StrokeRenderState(
    val pose: Matrix3x2fc,
    x1: Float,
    y1: Float,
    x2: Float,
    y2: Float,
    thickness: Float,
    val color: Int,
    val scissor: ScreenRectangle? = null
) : GuiElementRenderState {
    private var v0x: Float = 0f
    private var v0y: Float = 0f
    private var v1x: Float = 0f
    private var v1y: Float = 0f
    private var v2x: Float = 0f
    private var v2y: Float = 0f
    private var v3x: Float = 0f
    private var v3y: Float = 0f
    private var u0: Float = 0f
    private var v0: Float = 0f
    private var bounds: ScreenRectangle? = null

    override fun pipeline(): RenderPipeline = PIPELINE
    override fun textureSetup(): TextureSetup = TextureSetup.noTexture()
    override fun scissorArea(): ScreenRectangle? = scissor
    override fun bounds(): ScreenRectangle? = bounds

    init {
        val x0 = x2 - x1
        val y0 = y2 - y1
        val length = sqrt(x0 * x0 + y0 * y0)

        if (length != 0f) {
            val l2 = length / 2f
            val t2 = thickness / 2f
            val x3 = (x1 + x2) / 2f
            val y3 = (y1 + y2) / 2f
            val x4 = x0 / length
            val y4 = y0 / length
            val x5 = -y4
            val y5 = x4

            u0 = 1f + (1f / l2)
            v0 = 1f + (1f / t2)

            val x6 = l2 * u0
            val y6 = t2 * v0

            v0x = x3 - x4 * x6 - x5 * y6
            v0y = y3 - y4 * x6 - y5 * y6
            v1x = x3 - x4 * x6 + x5 * y6
            v1y = y3 - y4 * x6 + y5 * y6
            v2x = x3 + x4 * x6 + x5 * y6
            v2y = y3 + y4 * x6 + y5 * y6
            v3x = x3 + x4 * x6 - x5 * y6
            v3y = y3 + y4 * x6 - y5 * y6

            val x00 = min(min(v0x, v1x), min(v2x, v3x))
            val y00 = min(min(v0y, v1y), min(v2y, v3y))
            val x01 = max(max(v0x, v1x), max(v2x, v3x))
            val y01 = max(max(v0y, v1y), max(v2y, v3y))

            bounds = bounds(x00, y00, x01, y01, pose, scissor)
        }
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        if (bounds == null) return

        vertexConsumer.addVertexWith2DPose(pose, v0x, v0y).setColor(color).setUv(-u0, -v0)
        vertexConsumer.addVertexWith2DPose(pose, v1x, v1y).setColor(color).setUv(-u0, v0)
        vertexConsumer.addVertexWith2DPose(pose, v2x, v2y).setColor(color).setUv(u0, v0)
        vertexConsumer.addVertexWith2DPose(pose, v3x, v3y).setColor(color).setUv(u0, -v0)
    }

    companion object {
        //? if >= 26.2 {
        /*private val VERTEX_FORMAT = VertexFormat.builder(0)
            .addAttribute("Position", GpuFormat.RGB32_FLOAT)
            .addAttribute("Color", GpuFormat.RGBA8_UNORM)
            .addAttribute("UV0", GpuFormat.RG32_FLOAT)
            .build()
        *///? } else {
        private val VERTEX_FORMAT = VertexFormat.builder()
            .add("Position", VertexFormatElement.POSITION)
            .add("Color", VertexFormatElement.COLOR)
            .add("UV0", VertexFormatElement.UV0)
            .build()
        //? }

        private val PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.GUI_SNIPPET)
                //~ if >= 26.2 'withVertexFormat(VERTEX_FORMAT, VertexFormat.Mode.QUADS)' -> 'withVertexBinding(0, VERTEX_FORMAT)'
                .withVertexFormat(VERTEX_FORMAT, VertexFormat.Mode.QUADS)
                .withLocation(Identifier.fromNamespaceAndPath("cascade", "line"))
                .withVertexShader(Identifier.fromNamespaceAndPath("cascade", "core/shapes/stroke/stroke"))
                .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/shapes/stroke/stroke"))
                .build()
        )
    }
}
