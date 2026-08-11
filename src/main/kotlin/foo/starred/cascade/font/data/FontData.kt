@file:Suppress("ConstPropertyName")

package foo.starred.cascade.font.data

import com.google.gson.JsonObject
import foo.starred.cascade.Cascade.GSON
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.client.renderer.texture.DynamicTexture
import com.mojang.blaze3d.platform.NativeImage

class FontData(path: String) {
    val texture: AbstractTexture by lazy {
        DynamicTexture({ "cascade_font_$path" }, NativeImage.read(FontData::class.java.getResourceAsStream("$path.png") ?: error("Failed to find font png: $path.png")))
    }

    val glyphs: MutableMap<Int, GlyphData> = mutableMapOf()
    val all: MutableList<Int> = mutableListOf()

    val metrics: MetricsData
    val atlas: AtlasData
    val height: Float

    init {
        val stream = FontData::class.java.getResourceAsStream("$path.json") ?: error("Failed to find font json: $path.json")
        stream.reader().use { reader ->
            val obj = GSON.fromJson(reader, JsonObject::class.java)
            metrics = GSON.fromJson(obj["metrics"], MetricsData::class.java)
            atlas = GSON.fromJson(obj["atlas"], AtlasData::class.java)

            var h0 = 0f
            for (element in obj.getAsJsonArray("glyphs")) {
                val glyph = GSON.fromJson(element, GlyphData::class.java)
                glyphs[glyph.unicode] = glyph
                all += glyph.unicode

                h0 = glyph.planeBounds?.height()?.coerceAtLeast(h0) ?: continue
            }

            height = h0
        }
    }

    fun glyph(c: Char): GlyphData? {
        return glyphs[c.code]
    }

    fun glyph(): GlyphData? {
        return glyphs[random.random().code]
    }

    companion object {
        private const val random = "1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*()-=_+{}"
    }
}