package foo.starred.cascade.graphics.states.circle

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

class CircleRenderState(
    val pose: Matrix3x2fc,
    val x: Float,
    val y: Float,
    val radius: Float,
    val color: Int,
    val scissor: ScreenRectangle? = null
) : GuiElementRenderState {
    private var bounds: ScreenRectangle? = null

    override fun pipeline(): RenderPipeline = PIPELINE
    override fun textureSetup(): TextureSetup = TextureSetup.noTexture()
    override fun scissorArea(): ScreenRectangle? = scissor
    override fun bounds(): ScreenRectangle? = bounds

    init {
        if (radius > 0f) {
            val x0 = x - radius
            val y0 = y - radius
            val x1 = x + radius
            val y1 = y + radius
            bounds = bounds(x0, y0, x1, y1, pose, scissor)
        }
    }

    override fun buildVertices(vertexConsumer: VertexConsumer) {
        if (bounds == null) return

        val x0 = x - radius
        val y0 = y - radius
        val x1 = x + radius
        val y1 = y + radius

        vertexConsumer.addVertexWith2DPose(pose, x0, y0).setColor(color).setUv(-1f, -1f)
        vertexConsumer.addVertexWith2DPose(pose, x0, y1).setColor(color).setUv(-1f, 1f)
        vertexConsumer.addVertexWith2DPose(pose, x1, y1).setColor(color).setUv(1f, 1f)
        vertexConsumer.addVertexWith2DPose(pose, x1, y0).setColor(color).setUv(1f, -1f)
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
                .withLocation(Identifier.fromNamespaceAndPath("cascade", "circle"))
                .withVertexShader(Identifier.fromNamespaceAndPath("cascade", "core/shapes/circle/circle"))
                .withFragmentShader(Identifier.fromNamespaceAndPath("cascade", "core/shapes/circle/circle"))
                .build()
        )
    }
}
