package foo.starred.cascade.constraints.base

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPositionConstraint {
    fun x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
    fun y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
}