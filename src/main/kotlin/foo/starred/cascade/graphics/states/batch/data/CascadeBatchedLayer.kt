package foo.starred.cascade.graphics.states.batch.data

import com.mojang.blaze3d.pipeline.RenderPipeline
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup

class CascadeBatchedLayer {
    var x0 = Int.MAX_VALUE
    var y0 = Int.MAX_VALUE
    var x1 = Int.MIN_VALUE
    var y1 = Int.MIN_VALUE

    var pipeline: RenderPipeline? = null
    var texture: TextureSetup? = null
    var scissor: ScreenRectangle? = null

    var synced = 0

    fun matches(pipeline: RenderPipeline, texture: TextureSetup, scissor: ScreenRectangle?): Boolean {
        return this.pipeline === pipeline && this.texture == texture && this.scissor == scissor
    }

    fun reset() {
        x0 = Int.MAX_VALUE
        y0 = Int.MAX_VALUE
        x1 = Int.MIN_VALUE
        y1 = Int.MIN_VALUE

        pipeline = null
        texture = null
        scissor = null

        synced = 0
    }
}
