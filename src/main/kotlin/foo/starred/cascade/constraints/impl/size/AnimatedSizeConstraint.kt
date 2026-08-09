package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.animation.Animation
import foo.starred.cascade.animation.base.IAnimation
import foo.starred.cascade.animation.data.AnimatableFloat
import foo.starred.cascade.animation.enums.CascadeAnimations
import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class AnimatedSizeConstraint(width: Number, height: Number) : ISizeConstraint {
    private val w = AnimatableFloat(width)
    private val h = AnimatableFloat(height)

    override fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return w.value
    }

    override fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return h.value
    }

    fun animate(manager: Animation, w1: Number, h1: Number, duration: Number, easing: IAnimation = CascadeAnimations.LINEAR) {
        w.animate(manager, w1, duration, easing)
        h.animate(manager, h1, duration, easing)
    }

    companion object {
        fun <T : IPrimitiveElement<T>> T.animateSize(w1: Number, h1: Number, duration: Number, easing: IAnimation = CascadeAnimations.LINEAR): T {
            val manager = root.animations
            if (manager == null) {
                size = FixedSizeConstraint(w1, h1)
                return self
            }

            val current = size as? AnimatedSizeConstraint ?: AnimatedSizeConstraint(width, height).also { size = it }
            current.animate(manager, w1, h1, duration, easing)
            return self
        }
    }
}