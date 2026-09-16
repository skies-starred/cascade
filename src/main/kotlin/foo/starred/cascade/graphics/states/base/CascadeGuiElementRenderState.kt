@file:Suppress("PropertyName")

package foo.starred.cascade.graphics.states.base

import com.mojang.blaze3d.pipeline.RenderPipeline
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.state.gui.GuiElementRenderState

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
}
