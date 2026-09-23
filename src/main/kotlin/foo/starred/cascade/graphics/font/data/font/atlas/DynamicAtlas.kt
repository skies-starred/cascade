package foo.starred.cascade.graphics.font.data.font.atlas

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.renderer.texture.DynamicTexture
import org.lwjgl.system.MemoryUtil
import kotlin.math.max
import kotlin.math.min

class DynamicAtlas(val width: Int = 2048, val height: Int = 2048) {
    private var x = 0
    private var y = 0
    private var height0 = 0

    private var y1 = Int.MAX_VALUE
    private var y2 = Int.MIN_VALUE

    val native = NativeImage(NativeImage.Format.RGBA, width, height, true)
    val texture by lazy {
        DynamicTexture({ "cascade_dynamic_atlas" }, native)
    }

    var dirty = false
        private set

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

        dirty = true
        y1 = min(y1, y0)
        y2 = max(y2, y0 + h)

        return Pair(x0, y0)
    }

    fun upload() {
        if (!dirty) return

        val y01 = y1.coerceIn(0, height - 1)
        val y02 = y2.coerceIn(y01, height - 1)
        val i0 = y02 - y01 + 1

        val address = native.pointer + y01.toLong() * width * 4
        val size = i0 * width * 4
        val source = MemoryUtil.memByteBuffer(address, size)
        val encoder = RenderSystem.getDevice().createCommandEncoder()
        val destination = texture.getTexture()

        //~ if >= 26.2 'source, NativeImage.Format.RGBA, 0' -> 'source, 0'
        encoder.writeToTexture(destination, source, NativeImage.Format.RGBA, 0, 0, 0, y01, width, i0)

        dirty = false
        y1 = Int.MAX_VALUE
        y2 = Int.MIN_VALUE
    }

    fun close() {
        texture.close()
    }
}
