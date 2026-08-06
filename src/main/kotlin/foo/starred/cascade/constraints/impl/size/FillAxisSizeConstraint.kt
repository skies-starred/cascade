package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.constraints.impl.data.FillAxis
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class FillAxisSizeConstraint(val axis: FillAxis, fixed: Number, padding: Number = 0) : ISizeConstraint {
    val fixed: Float = fixed.toFloat()
    val padding: Float = padding.toFloat()

    override fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        if (axis == FillAxis.HORIZONTAL) return (parent.width - padding * 2).coerceAtLeast(0f)
        return fixed
    }

    override fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        if (axis == FillAxis.VERTICAL) return (parent.height - padding * 2).coerceAtLeast(0f)
        return fixed
    }
}