package foo.starred.cascade.constraints.impl.position

import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class MixedPositionConstraint(val x: IPositionConstraint, val y: IPositionConstraint) : IPositionConstraint {
    override fun x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return x.x(element, parent)
    }

    override fun y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return y.y(element, parent)
    }
}