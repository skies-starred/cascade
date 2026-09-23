package foo.starred.cascade.graphics.font.data.font.impl

import foo.starred.cascade.graphics.font.data.AtlasData
import foo.starred.cascade.graphics.font.data.BoundsData
import foo.starred.cascade.graphics.font.data.GlyphData
import foo.starred.cascade.graphics.font.data.MetricsData
import foo.starred.cascade.graphics.font.data.font.atlas.DynamicAtlas
import foo.starred.cascade.graphics.font.data.font.base.IFontData
import net.minecraft.client.renderer.texture.AbstractTexture
import org.lwjgl.stb.STBTTFontinfo
import org.lwjgl.stb.STBTruetype
import org.lwjgl.system.MemoryUtil
import java.io.File
import java.io.InputStream
import java.nio.ByteBuffer

class TtfFontData(stream: InputStream, bakeSize: Float = 48f) : IFontData {
    constructor(path: String, bakeSize: Float = 48f) : this(resolve(path).inputStream(), bakeSize)

    private val info: STBTTFontinfo = STBTTFontinfo.create()
    private val data: ByteBuffer
    private val scale: Float

    private val atlases = mutableListOf(DynamicAtlas(2048, 2048))
    private val glyphs = mutableMapOf<Int, GlyphData>()

    override val metrics: MetricsData
    override val atlas: AtlasData
    override val height: Float
    override val texture: AbstractTexture
        get() = atlases[0].texture

    init {
        val bytes = stream.use { it.readBytes() }
        data = MemoryUtil.memAlloc(bytes.size)
        data.put(bytes).flip()
        if (!STBTruetype.stbtt_InitFont(info, data)) error("Failed to initialize STB Truetype font")

        scale = STBTruetype.stbtt_ScaleForMappingEmToPixels(info, bakeSize)

        val ascent = IntArray(1)
        val descent = IntArray(1)
        val gap = IntArray(1)
        STBTruetype.stbtt_GetFontVMetrics(info, ascent, descent, gap)

        val ascender = ascent[0] * scale
        val descender = (ascender + (descent[0] * scale) - bakeSize) / 2f
        val height0 = ascender - (descent[0] * scale) + (gap[0] * scale)

        metrics = MetricsData(bakeSize.toInt(), height0 / bakeSize, ascender / bakeSize, descender / bakeSize, -0.1f, 0.05f)
        atlas = AtlasData("sdf", 4, 127, bakeSize, 2048, 2048, "bottom")
        height = ascender / bakeSize
    }

    fun close() {
        MemoryUtil.memFree(data)

        for (atlas in atlases) {
            atlas.close()
        }
    }

    override fun upload() {
        for (atlas in atlases) {
            atlas.upload()
        }
    }

    override fun preload(chars: Iterable<Char>) {
        for (c in chars) {
            if (glyphs.containsKey(c.code)) continue
            val g = glyph(c.code, false) ?: continue

            glyphs[c.code] = g
        }

        upload()
    }

    override fun texture(page: Int): AbstractTexture {
        return atlases[page].texture
    }

    override fun glyph(c: Char): GlyphData? {
        return glyphs.getOrPut(c.code) {
            glyph(c.code, false) ?: return null
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

        var page = atlases.lastIndex
        var packed = atlases.last().pack(width, height)
        if (packed == null) {
            val next = DynamicAtlas(2048, 2048)

            atlases += next
            page = atlases.lastIndex
            packed = next.pack(width, height) ?: run {
                STBTruetype.stbtt_FreeSDF(sdf)
                return null
            }
        }

        val (px, py) = packed
        val atlas1 = atlases[page]

        for (y in 0 until height) {
            for (x in 0 until width) {
                val v = sdf[y * width + x].toInt() and 0xFF
                atlas1.native.setPixel(px + x, py + y, -0x1000000 or (v shl 16) or (v shl 8) or v)
            }
        }

        STBTruetype.stbtt_FreeSDF(sdf)
        if (upload) atlas1.upload()

        val left0 = x0[0].toFloat() / atlas.size
        val top0 = -y0[0].toFloat() / atlas.size
        val right0 = (x0[0] + width).toFloat() / atlas.size
        val bottom0 = -(y0[0] + height).toFloat() / atlas.size

        val left1 = px.toFloat()
        val right1 = (px + width).toFloat()
        val bottom1 = (atlas1.height - py - height).toFloat()
        val top1 = (atlas1.height - py).toFloat()

        return GlyphData(unicode, advance[0] * scale / atlas.size, BoundsData(left0, bottom0, right0, top0), BoundsData(left1, bottom1, right1, top1), page)
    }

    companion object {
        private const val RANDOM = "1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*()-=_+{}"

        fun resolve(path: String): File {
            val file = File(path)
            if (file.exists()) return file

            val home = System.getProperty("user.home")
            val dirs = when {
                System.getProperty("os.name").startsWith("Windows", true) -> {
                    listOfNotNull(File(System.getenv("WINDIR") ?: "C:\\Windows", "Fonts"), System.getenv("LOCALAPPDATA")?.let { File(it, "Microsoft\\Windows\\Fonts") })
                }

                System.getProperty("os.name").startsWith("Mac", true) -> {
                    listOf(File("/Library/Fonts"), File("/System/Library/Fonts"), File(home, "Library/Fonts"))
                }

                else -> {
                    listOf(File("/usr/share/fonts"), File("/usr/local/share/fonts"), File(home, ".fonts"), File(home, ".local/share/fonts"))
                }
            }

            for (dir in dirs) {
                if (!dir.isDirectory) continue
                dir.walk().find { it.isFile && it.nameWithoutExtension.equals(path, true) }?.let { return it }
            }

            return file
        }
    }
}
