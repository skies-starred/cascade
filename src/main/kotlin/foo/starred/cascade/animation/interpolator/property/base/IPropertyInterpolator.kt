package foo.starred.cascade.animation.interpolator.property.base

import foo.starred.cascade.animation.interpolator.property.impl.ColorPropertyInterpolator
import foo.starred.cascade.animation.interpolator.property.impl.NumberPropertyInterpolator
import foo.starred.cascade.animation.interpolator.property.impl.OffsetPropertyInterpolator
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset

fun interface IPropertyInterpolator<T> {
    fun get(from: T, to: T, progress: Float): T

    companion object {
        fun of(value: Any?): IPropertyInterpolator<*>? {
            return when (value) {
                is Number -> NumberPropertyInterpolator
                is CascadeGeometricOffset -> OffsetPropertyInterpolator
                is CascadeGeometricColor -> ColorPropertyInterpolator
                else -> null
            }
        }
    }
}
