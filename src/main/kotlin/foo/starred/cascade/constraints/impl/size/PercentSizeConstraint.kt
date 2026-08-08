package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class PercentSizeConstraint(w: Number, h: Number) : ISizeConstraint {
    val w: Float = w.toFloat()
    val h: Float = h.toFloat()

    override fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return (parent.width / 100f) * w
    }

    override fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return (parent.height / 100f) * h
    }
}