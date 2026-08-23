package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import java.util.concurrent.CopyOnWriteArrayList

interface IPrimitiveEffects<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    val effects: CopyOnWriteArrayList<IEffect>

    fun <E : IEffect> effect(effect: E): E {
        effect.element = self
        effects.add(effect)
        return effect
    }

    fun <E : IEffect> effect(effect: E, block: E.() -> Unit): E {
        return effect(effect.apply(block))
    }
}
