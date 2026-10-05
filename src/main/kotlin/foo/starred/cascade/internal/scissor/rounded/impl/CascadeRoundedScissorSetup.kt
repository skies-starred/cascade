package foo.starred.cascade.internal.scissor.rounded.impl

import com.mojang.blaze3d.pipeline.TextureTarget
import com.mojang.blaze3d.systems.RenderSystem

//? if >= 26.2
//import com.mojang.blaze3d.GpuFormat

object CascadeRoundedScissorSetup {
    //? if >= 26.2
    //private val CLEAR_COLOR = org.joml.Vector4f(0f, 0f, 0f, 0f)

    var target: TextureTarget? = null
        private set

    var width: Int = 0
        private set

    var height: Int = 0
        private set

    fun clear() {
        val target = target ?: return
        val color = target.colorTexture ?: return
        val encoder = RenderSystem.getDevice().createCommandEncoder()
        //~ if >= 26.2 '0' -> 'CLEAR_COLOR'
        encoder.clearColorTexture(color, 0)
    }

    fun validate(width: Int, height: Int) {
        if (this.width == width && this.height == height && target != null) return

        target?.destroyBuffers()
        target = null
        this.width = width
        this.height = height
        target = target("cascade_rounded_scissor_scratch", width, height)
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
