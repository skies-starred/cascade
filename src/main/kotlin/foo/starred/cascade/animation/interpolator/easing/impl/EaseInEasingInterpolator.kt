package foo.starred.cascade.animation.interpolator.easing.impl

import foo.starred.cascade.animation.interpolator.easing.base.IEasingInterpolator

object EaseInEasingInterpolator : IEasingInterpolator {
    override fun get(float: Float): Float {
        return float * float
    }
}
