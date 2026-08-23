package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement

interface IPrimitiveEffect<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    val effects: MutableList<IEffect>

    fun <E : IEffect> effect(effect: E): E {
        effect.element = self
        effects.add(effect)
        return effect
    }

    fun <E : IEffect> effect(effect: E, block: E.() -> Unit): E {
        return effect(effect.apply(block))
    }
}
