package foo.starred.cascade.graphics.font.data

data class GlyphData(
    val unicode: Int,
    val advance: Float,
    val planeBounds: BoundsData?,
    val atlasBounds: BoundsData?
)
