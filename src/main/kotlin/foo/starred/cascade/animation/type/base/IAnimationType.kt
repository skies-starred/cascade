package foo.starred.cascade.animation.type.base

interface IAnimationType {
    var function: (() -> Unit)?
    fun advance(delta: Float): Boolean
}
