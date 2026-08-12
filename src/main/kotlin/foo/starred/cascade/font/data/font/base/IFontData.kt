package foo.starred.cascade.font.data.font.base

import foo.starred.cascade.font.data.AtlasData
import foo.starred.cascade.font.data.GlyphData
import foo.starred.cascade.font.data.MetricsData
import net.minecraft.client.renderer.texture.AbstractTexture

interface IFontData {
    val texture: AbstractTexture
    val metrics: MetricsData
    val atlas: AtlasData
    val height: Float

    fun glyph(c: Char): GlyphData?
    fun glyph(): GlyphData?
    fun preload(chars: Iterable<Char>) {}
}