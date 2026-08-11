package foo.starred.cascade.primitives.utils

import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import foo.starred.cascade.Cascade.client
import net.minecraft.client.gui.render.TextureSetup

object Blur {
    private var last = 0L
    private var width0 = 0
    private var height0 = 0

    var target: TextureTarget? = null
        private set

    fun setup(): TextureSetup {
        capture()
        return TextureSetup.singleTexture(target!!.colorTextureView!!, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))
    }

    fun capture() {
        val now = System.nanoTime()
        if (target != null && now - last < 16_666_666) return
        last = now

        val width00 = client.window.width
        val height00 = client.window.height

        if (target == null || width0 != width00 || height0 != height00) {
            target?.destroyBuffers()
            //~ if >= 26.2 'false)' -> 'false, com.mojang.blaze3d.GpuFormat.RGBA8_UNORM)'
            target = TextureTarget("cascade_blur_target", width00, height00, false)
            width0 = width00
            height0 = height00
        }

        //~ if >= 26.2 'client.mainRenderTarget' -> 'client.gameRenderer.mainRenderTarget()'
        RenderSystem.getDevice().createCommandEncoder().copyTextureToTexture(client.mainRenderTarget.colorTexture!!, target!!.colorTexture!!, 0, 0, 0, 0, 0, width00, height00)
    }
}