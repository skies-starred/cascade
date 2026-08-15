package foo.starred.cascade.animation.base

interface IAnimatable {
    var function: (() -> Unit)?
    fun advance(delta: Float): Boolean
}