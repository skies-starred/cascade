@file:Suppress("PropertyName")

package foo.starred.cascade.graphics.states.base

import com.mojang.blaze3d.pipeline.RenderPipeline
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import org.joml.Matrix3x2fc
import kotlin.math.ceil
import kotlin.math.floor

abstract class CascadeGuiElementRenderState(
    val _pipeline: RenderPipeline,
    val _scissor: ScreenRectangle? = null,
    val _bounds: ScreenRectangle? = null
) : GuiElementRenderState {
    override fun pipeline(): RenderPipeline {
        return _pipeline
    }

    override fun textureSetup(): TextureSetup {
        return TextureSetup.noTexture()
    }

    override fun scissorArea(): ScreenRectangle? {
        return _scissor
    }

    override fun bounds(): ScreenRectangle? {
        return _bounds
    }

    fun submit(graphics: GuiGraphicsExtractor) {
        graphics.guiRenderState.addGuiElement(this)
    }

    companion object {
        fun bounds(x0: Float, y0: Float, x1: Float, y1: Float, pose: Matrix3x2fc, scissor: ScreenRectangle?): ScreenRectangle? {
            val bounds = ScreenRectangle(floor(x0.toDouble()).toInt(), floor(y0.toDouble()).toInt(), ceil((x1 - x0).toDouble()).toInt(), ceil((y1 - y0).toDouble()).toInt()).transformMaxBounds(pose)
            return if (scissor != null) scissor.intersection(bounds) else bounds
        }
    }
}
