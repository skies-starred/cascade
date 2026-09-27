package foo.starred.cascade.animation.interpolator.property.impl

import foo.starred.cascade.animation.interpolator.property.base.IPropertyInterpolator

object NumberPropertyInterpolator : IPropertyInterpolator<Number> {
    override fun get(from: Number, to: Number, progress: Float): Number {
        val value = from.toDouble() + (to.toDouble() - from.toDouble()) * progress

        return when (to) {
            is Float -> value.toFloat()
            is Double -> value
            is Int -> value.toInt()
            is Long -> value.toLong()
            else -> value
        }
    }
}
