@file:Suppress("ConstPropertyName")

package foo.starred.cascade.font.data

import com.google.gson.JsonObject
import foo.starred.cascade.Cascade.GSON
import foo.starred.cascade.Cascade.client
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.resources.Identifier

class FontData(identifier: Identifier) {
    val texture: AbstractTexture = client.textureManager.getTexture(identifier.withSuffix(".png"))

    val glyphs: MutableMap<Int, GlyphData> = mutableMapOf()
    val all: MutableList<Int> = mutableListOf()

    val metrics: MetricsData
    val atlas: AtlasData
    val height: Float

    init {
        client.resourceManager.getResourceOrThrow(identifier.withSuffix(".json")).openAsReader().use { reader ->
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