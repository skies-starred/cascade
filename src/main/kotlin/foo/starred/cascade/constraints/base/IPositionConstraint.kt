@file:Suppress("FunctionName")

package foo.starred.cascade.constraints.base

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPositionConstraint {
    fun _x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
    fun _y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
}
