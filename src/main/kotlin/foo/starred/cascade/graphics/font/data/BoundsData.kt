package foo.starred.cascade.graphics.font.data

data class BoundsData(
    val left: Float,
    val bottom: Float,
    val right: Float,
    val top: Float
) {
    fun width(): Float {
        return right - left
    }

    fun height(): Float {
        return top - bottom
    }
}
