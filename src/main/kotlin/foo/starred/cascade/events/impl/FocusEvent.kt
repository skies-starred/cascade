package foo.starred.cascade.events.impl

import foo.starred.cascade.events.base.UIEvent
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

sealed class FocusEvent {
    data class Gain(
        val element: IPrimitiveElement<*>
    ) : UIEvent()

    data class Lose(
        val element: IPrimitiveElement<*>
    ) : UIEvent()
}