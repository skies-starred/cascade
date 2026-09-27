package foo.starred.cascade.primitives.base.abstract

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.events.base.UIEvent
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import foo.starred.cascade.primitives.base.interfaces.IPrimitiveSelf

abstract class AbstractPrimitiveExtensions<T : IPrimitiveElement<T>> : IPrimitiveSelf<T> {
    inline fun <reified E : IEffect> effect(id: String = "primary"): E? {
        for (e in self.effects) if (e.id == id && e is E) return e
        return null
    }

    inline fun <reified E : IEffect> effect(id: String = "primary", block: E.() -> Unit): E? {
        return effect<E>(id)?.apply(block)
    }

    inline fun <reified E : IEffect> disown(id: String? = "primary"): Boolean {
        return self.effects.removeIf { it is E && (id == null || it.id == id) }
    }

    inline fun <reified E : UIEvent> on(noinline listener: E.() -> Unit): T {
        return self.on(E::class.java, listener)
    }
}
