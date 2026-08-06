package foo.starred.cascade.constraints.base

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface ISizeConstraint {
    fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
    fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
}