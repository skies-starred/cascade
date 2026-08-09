package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.animation.Animation
import foo.starred.cascade.animation.base.IAnimation
import foo.starred.cascade.animation.data.AnimatableFloat
import foo.starred.cascade.animation.enums.CascadeAnimations
import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class AnimatedPositionConstraint(x: Number, y: Number) : IPositionConstraint {
    private val x = AnimatableFloat(x)
    private val y = AnimatableFloat(y)

    override fun x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return x.value
    }

    override fun y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return y.value
    }

    fun animateTo(manager: Animation, x1: Number, y1: Number, duration: Number, easing: IAnimation = CascadeAnimations.LINEAR) {
        x.animate(manager, x1, duration, easing)
        y.animate(manager, y1, duration, easing)
    }

    companion object {
        fun <T : IPrimitiveElement<T>> T.animatePosition(x1: Number, y1: Number, duration: Number, easing: IAnimation = CascadeAnimations.LINEAR): T {
            val manager = root.animations
            if (manager == null) {
                position = FixedPositionConstraint(x1, y1)
                return self
            }

            val current = position as? AnimatedPositionConstraint ?: AnimatedPositionConstraint(x, y).also { position = it }
            current.animateTo(manager, x1, y1, duration, easing)
            return self
        }
    }
}