package foo.starred.cascade.primitives.base.interfaces

import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import java.util.concurrent.CopyOnWriteArrayList

interface IPrimitiveEffects<T> : IPrimitiveSelf<T> where T : IPrimitiveElement<T> {
    val effects: CopyOnWriteArrayList<IEffect>

    operator fun contains(effect: IEffect): Boolean {
        return effect in effects
    }

    fun <E : IEffect> effect(effect: E): E {
        return effect(effect.id, effect)
    }

    fun <E : IEffect> effect(id: String, effect: E): E {
        effect.id = id

        effects.removeIf { it.id == id && it::class == effect::class }
        effects.add(effect)
        return effect
    }

    fun disown(effect: IEffect): Boolean {
        return effects.remove(effect)
    }
}
