package foo.starred.cascade.graphics.font.data.font.impl

import com.google.gson.JsonObject
import com.mojang.blaze3d.platform.NativeImage
import foo.starred.cascade.Cascade
import foo.starred.cascade.graphics.font.data.AtlasData
import foo.starred.cascade.graphics.font.data.GlyphData
import foo.starred.cascade.graphics.font.data.MetricsData
import foo.starred.cascade.graphics.font.data.font.base.IFontData
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.DynamicTexture

class MsdfFontData(path: String) : IFontData {
    override val texture: AbstractTexture by lazy {
        DynamicTexture({ "cascade_font_$path" }, NativeImage.read(MsdfFontData::class.java.getResourceAsStream("$path.png") ?: error("Failed to find font png: $path.png")))
    }

    override val metrics: MetricsData
    override val atlas: AtlasData
    override val height: Float

    val glyphs: MutableMap<Int, GlyphData> = mutableMapOf()
    val all: MutableList<Int> = mutableListOf()

    init {
        val stream = MsdfFontData::class.java.getResourceAsStream("$path.json") ?: error("Failed to find font json: $path.json")
        stream.reader().use { reader ->
            val obj = Cascade.GSON.fromJson(reader, JsonObject::class.java)
            metrics = Cascade.GSON.fromJson(obj["metrics"], MetricsData::class.java)
            atlas = Cascade.GSON.fromJson(obj["atlas"], AtlasData::class.java)

            var h0 = 0f
            for (element in obj.getAsJsonArray("glyphs")) {
                val glyph = Cascade.GSON.fromJson(element, GlyphData::class.java)
                glyphs[glyph.unicode] = glyph
                all += glyph.unicode

                h0 = glyph.planeBounds?.height()?.coerceAtLeast(h0) ?: continue
            }

            height = h0
        }
    }

    override fun glyph(c: Char): GlyphData? {
        return glyphs[c.code]
    }

    override fun glyph(): GlyphData? {
        return glyphs[RANDOM.random().code]
    }

    companion object {
        private const val RANDOM = "1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*()-=_+{}"
    }
}
