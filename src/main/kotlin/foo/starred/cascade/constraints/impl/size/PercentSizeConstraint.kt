package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class PercentSizeConstraint(width: Number, height: Number) : ISizeConstraint {
    val width: Float = width.toFloat()
    val height: Float = height.toFloat()

    override fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return (parent.width / 100f) * width
    }

    override fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return (parent.height / 100f) * height
    }
}
