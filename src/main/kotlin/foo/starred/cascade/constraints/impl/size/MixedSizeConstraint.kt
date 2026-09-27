package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class MixedSizeConstraint(val width: ISizeConstraint, val height: ISizeConstraint) : ISizeConstraint {
    override fun _width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return width._width(element, parent)
    }

    override fun _height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return height._height(element, parent)
    }
}
