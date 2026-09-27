package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class MixedPositionConstraint(val x: IPositionConstraint, val y: IPositionConstraint) : IPositionConstraint {
    override fun _x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return x._x(element, parent)
    }

    override fun _y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return y._y(element, parent)
    }
}
