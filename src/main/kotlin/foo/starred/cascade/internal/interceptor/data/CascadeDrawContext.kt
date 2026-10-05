package foo.starred.cascade.internal.interceptor.data

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.pipeline.RenderTarget
import net.minecraft.client.gui.render.GuiRenderer

abstract class CascadeDrawContext(
    val draws: List<GuiRenderer.Draw>,
    val start: Int,
    val end: Int,
    val target: RenderTarget
) {
    abstract fun execute(target: RenderTarget, start: Int, end: Int)

    fun index(pipeline: RenderPipeline, from: Int = start): Int {
        for (i in from until end) {
            if (draws[i].pipeline() == pipeline) return i
        }

        return -1
    }
}
