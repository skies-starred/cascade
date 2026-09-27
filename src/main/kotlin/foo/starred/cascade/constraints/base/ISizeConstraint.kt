@file:Suppress("FunctionName")

package foo.starred.cascade.constraints.base

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface ISizeConstraint {
    fun _width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
    fun _height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
}
