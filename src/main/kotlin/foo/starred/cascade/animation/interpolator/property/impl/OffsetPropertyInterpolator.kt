package foo.starred.cascade.animation.interpolator.property.impl

import foo.starred.cascade.animation.interpolator.property.base.IPropertyInterpolator
import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset

object OffsetPropertyInterpolator : IPropertyInterpolator<CascadeGeometricOffset> {
    override fun get(from: CascadeGeometricOffset, to: CascadeGeometricOffset, progress: Float): CascadeGeometricOffset {
        val x = from.x + (to.x - from.x) * progress
        val y = from.y + (to.y - from.y) * progress

        return CascadeGeometricOffset(x, y)
    }
}
