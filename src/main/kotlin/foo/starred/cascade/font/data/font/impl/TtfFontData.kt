package foo.starred.cascade.font.data.font.impl

import foo.starred.cascade.font.data.AtlasData
import foo.starred.cascade.font.data.BoundsData
import foo.starred.cascade.font.data.GlyphData
import foo.starred.cascade.font.data.MetricsData
import foo.starred.cascade.font.data.font.atlas.DynamicAtlas
import foo.starred.cascade.font.data.font.base.IFontData
import net.minecraft.client.renderer.texture.AbstractTexture
import org.lwjgl.stb.STBTTFontinfo
import org.lwjgl.stb.STBTruetype
import org.lwjgl.system.MemoryUtil
import java.io.InputStream
import java.nio.ByteBuffer

class TtfFontData(stream: InputStream, bakeSize: Float = 48f) : IFontData {
    private val info: STBTTFontinfo = STBTTFontinfo.create()
    private val data: ByteBuffer
    private val scale: Float

    private val atlas0 = DynamicAtlas(2048, 2048)
    private val glyphs = mutableMapOf<Int, GlyphData>()

    override val metrics: MetricsData
    override val atlas: AtlasData
    override val height: Float
    override val texture: AbstractTexture
        get() = atlas0.texture

    init {
        val bytes = stream.readBytes()
        data = MemoryUtil.memAlloc(bytes.size)
        data.put(bytes).flip()
        if (!STBTruetype.stbtt_InitFont(info, data)) error("Failed to initialize STB Truetype font")

        scale = STBTruetype.stbtt_ScaleForMappingEmToPixels(info, bakeSize)

        val ascent = IntArray(1)
        val descent = IntArray(1)
        val gap = IntArray(1)
        STBTruetype.stbtt_GetFontVMetrics(info, ascent, descent, gap)

        val ascender = ascent[0] * scale
        val descender = descent[0] * scale
        val height0 = ascender - descender + (gap[0] * scale)

        metrics = MetricsData(bakeSize.toInt(), height0 / bakeSize, ascender / bakeSize, descender / bakeSize, -0.1f, 0.05f)
        atlas = AtlasData("sdf", 4, 127, bakeSize, 2048, 2048, "bottom")
        height = ascender / bakeSize
    }

    fun close() {
        MemoryUtil.memFree(data)
        atlas0.native.close()
    }

    override fun preload(chars: Iterable<Char>) {
        var uploaded = false

        for (c in chars) {
            if (glyphs.containsKey(c.code)) continue
            val g = glyph(c.code, false) ?: continue

            glyphs[c.code] = g
            uploaded = true
        }

        if (uploaded) {
            atlas0.texture.upload()
        }
    }

    override fun glyph(c: Char): GlyphData? {
        return glyphs.getOrPut(c.code) {
            glyph(c.code) ?: return null
        }
    }

    override fun glyph(): GlyphData? {
        return glyph(RANDOM.random())
    }

    private fun glyph(unicode: Int, upload: Boolean = true): GlyphData? {
        val advance = IntArray(1)
        STBTruetype.stbtt_GetCodepointHMetrics(info, unicode, advance, IntArray(1))

        val w = IntArray(1)
        val h = IntArray(1)
        val x0 = IntArray(1)
        val y0 = IntArray(1)

        val sdf = STBTruetype.stbtt_GetCodepointSDF(info, scale, unicode, 2, 127.toByte(), 127f, w, h, x0, y0) ?: return GlyphData(unicode, advance[0] * scale / atlas.size, null, null)

        val width = w[0]
        val height = h[0]

        val (px, py) = atlas0.pack(width, height) ?: run {
            STBTruetype.stbtt_FreeSDF(sdf)
            return null
        }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val v = sdf[y * width + x].toInt() and 0xFF
                atlas0.native.setPixel(px + x, py + y, -0x1000000 or (v shl 16) or (v shl 8) or v)
            }
        }

        STBTruetype.stbtt_FreeSDF(sdf)
        if (upload) atlas0.texture.upload()

        val left0 = x0[0].toFloat() / atlas.size
        val top0 = -y0[0].toFloat() / atlas.size
        val right0 = (x0[0] + width).toFloat() / atlas.size
        val bottom0 = -(y0[0] + height).toFloat() / atlas.size

        val left1 = px.toFloat()
        val right1 = (px + width).toFloat()
        val bottom1 = (atlas0.height - py - height).toFloat()
        val top1 = (atlas0.height - py).toFloat()

        return GlyphData(unicode, advance[0] * scale / atlas.size, BoundsData(left0, bottom0, right0, top0), BoundsData(left1, bottom1, right1, top1))
    }

    companion object {
        private const val RANDOM = "1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*()-=_+{}"
    }
}