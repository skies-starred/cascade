package foo.starred.cascade.font.data.font.atlas

import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.client.renderer.texture.DynamicTexture

class DynamicAtlas(val width: Int, val height: Int) {
    private var x = 0
    private var y = 0
    private var height0 = 0

    val native = NativeImage(NativeImage.Format.RGBA, width, height, true)
    val texture by lazy {
        DynamicTexture({ "cascade_dynamic_atlas" }, native)
    }

    @Synchronized
    fun pack(w: Int, h: Int): Pair<Int, Int>? {
        if (w > width) return null
        if (h > height) return null

        var x0 = x
        var y0 = y
        var h0 = height0

        if (x0 + w + 4 > width) {
            y0 += h0 + 4
            x0 = 0
            h0 = 0
        }

        if (y0 + h > height) {
            return null
        }

        if (h > h0) {
            h0 = h
        }

        x = x0 + w + 4
        y = y0
        height0 = h0

        return Pair(x0, y0)
    }
}