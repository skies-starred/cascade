package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class FixedPositionConstraint(x: Number, y: Number) : IPositionConstraint {
    var x: Float = x.toFloat()
    var y: Float = y.toFloat()

    override fun _x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return parent.x + x
    }

    override fun _y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return parent.y + y
    }
}
