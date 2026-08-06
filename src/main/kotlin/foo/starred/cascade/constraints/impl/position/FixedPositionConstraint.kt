package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class FixedPositionConstraint(x: Number, y: Number) : IPositionConstraint {
    val x: Float = x.toFloat()
    val y: Float = y.toFloat()

    override fun x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return parent.x + x
    }

    override fun y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return parent.y + y
    }
}