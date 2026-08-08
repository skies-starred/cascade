package foo.starred.cascade.constraints.impl.size

import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

class MixedSizeConstraint(val w: ISizeConstraint, val h: ISizeConstraint) : ISizeConstraint {
    override fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return w.width(element, parent)
    }

    override fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float {
        return h.height(element, parent)
    }
}