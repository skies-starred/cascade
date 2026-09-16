package foo.starred.cascade.utils.blur.impl

import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
//? if >= 26.2 {
/*import com.mojang.blaze3d.vertex.VertexFormatElement
*///?}
import foo.starred.cascade.Cascade.client
import foo.starred.cascade.utils.blur.data.CascadeBlurBlend
import foo.starred.cascade.utils.blur.data.CascadeBlurPipeline
import net.minecraft.client.gui.render.TextureSetup
import kotlin.math.max

object CascadeBlurHelper {
    private val targets = arrayOfNulls<TextureTarget>(6)
    private val tiers = arrayOfNulls<TextureTarget>(5)

    private var time = -1L
    private var mask = 0
    private var width = 0
    private var height = 0

    fun setup(blend: CascadeBlurBlend): TextureSetup {
        val width = client.window.width
        val height = client.window.height
        validate(width, height)

        val a = ensure(blend.tier0, width, height)
        val b = ensure(blend.tier1, width, height)
        mask = mask or (1 shl blend.tier0) or (1 shl blend.tier1)

        val sampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR)
        //~ if >= 26.2 'client.mainRenderTarget' -> 'client.gameRenderer.mainRenderTarget()'
        val fallback = client.mainRenderTarget.colorTextureView!!
        val texture0 = a?.colorTextureView ?: fallback
        val texture1 = b?.colorTextureView ?: fallback
        return TextureSetup.doubleTexture(texture0, sampler, texture1, sampler)
    }

    fun capture() {
        val now = System.nanoTime()
        if (now - time < 16_666_666L) return
        time = now

        val raw = targets[0] ?: return
        //~ if >= 26.2 'client.mainRenderTarget' -> 'client.gameRenderer.mainRenderTarget()'
        val source = client.mainRenderTarget.colorTexture ?: return
        val destination = raw.colorTexture ?: return

        RenderSystem.getDevice().createCommandEncoder().copyTextureToTexture(source, destination, 0, 0, 0, 0, 0, width, height)

        for (i in 1..5) {
            val bit = 1 shl i
            if ((mask and bit) == 0) continue
            val target = targets[i] ?: continue

            pass(i, target)
        }
    }

    private fun ensure(level: Int, width: Int, height: Int): TextureTarget? {
        if (level == 0) return targets[0]

        val existing = targets[level]
        if (existing != null) return existing

        return target("cascade_blur_final_$level", width, height).also { targets[level] = it }
    }

    private fun pass(tier: Int, dest: TextureTarget) {
        val encoder = RenderSystem.getDevice().createCommandEncoder()
        val sampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR)
        val source0 = targets[0]!!.colorTextureView!!

        for (i in 0 until tier) {
            val source = if (i == 0) source0 else tiers[i - 1]!!.colorTextureView!!

            CascadeBlurPipeline.pass(encoder, "Kawase Down ${i + 1}", tiers[i]!!, CascadeBlurPipeline.DOWN, source, sampler)
        }

        for (i in (tier - 1) downTo 0) {
            val source = tiers[i]!!.colorTextureView!!
            val target = if (i == 0) dest else tiers[i - 1]!!

            CascadeBlurPipeline.pass(encoder, "Kawase Up ${tier - i}", target, CascadeBlurPipeline.UP, source, sampler)
        }
    }

    private fun destroy() {
        for (i in targets.indices) {
            targets[i]?.destroyBuffers()
            targets[i] = null
        }

        for (i in tiers.indices) {
            tiers[i]?.destroyBuffers()
            tiers[i] = null
        }

        mask = 0
        time = -1L
    }

    private fun validate(width1: Int, height1: Int) {
        if (width == width1 && height == height1 && targets[0] != null) return

        destroy()
        width = width1
        height = height1

        targets[0] = target("cascade_blur_raw", width1, height1)
        for (i in tiers.indices) {
            val scale = 1 shl (i + 1)
            tiers[i] = target("cascade_blur_t${i + 1}", max(1, width1 / scale), max(1, height1 / scale))
        }
    }

    private fun target(name: String, width: Int, height: Int): TextureTarget {
        //? if >= 26.3 {
        /*return TextureTarget(name, width, height, GpuFormat.RGBA8_UNORM, null)
        *///?} elif 26.2 {
        /*return TextureTarget(name, width, height, false, GpuFormat.RGBA8_UNORM)
        *///?} else {
        return TextureTarget(name, width, height, false)
        //?}
    }
}
