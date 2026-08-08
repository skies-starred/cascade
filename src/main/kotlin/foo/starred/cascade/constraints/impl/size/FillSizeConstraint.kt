package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class FillSizeConstraint(padding: Number = 0) : ISizeConstraint {
    val padding: Float = padding.toFloat()

    override fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return (parent.width - padding * 2).coerceAtLeast(0f)
    }

    override fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return (parent.height - padding * 2).coerceAtLeast(0f)
    }
}