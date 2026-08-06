@file:Suppress("Unchecked_Cast", "Unused")

package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.events.base.UIEvent
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveEvents<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    val listeners: MutableMap<Class<out UIEvent>, MutableList<UIEvent.() -> Unit>>

    fun <E : UIEvent> on(
        klass: Class<E>,
        listener: E.() -> Unit
    ): T {
        listeners.getOrPut(klass) { mutableListOf() }.add(listener as UIEvent.() -> Unit)
        return self
    }

    fun post(event: UIEvent): Boolean {
        val a = listeners[event::class.java] ?: return false
        for (b in a) b(event)
        return event.cancelled
    }
}