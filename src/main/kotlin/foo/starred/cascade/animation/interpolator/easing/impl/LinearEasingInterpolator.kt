package foo.starred.cascade.animation.interpolator.easing.impl

import foo.starred.cascade.animation.interpolator.easing.base.IEasingInterpolator

object LinearEasingInterpolator : IEasingInterpolator {
    override fun get(float: Float): Float {
        return float
    }
}
