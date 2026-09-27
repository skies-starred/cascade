@file:Suppress("Unused")

package foo.starred.cascade.animation.manager

import foo.starred.cascade.animation.type.base.IAnimationType
import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import java.util.concurrent.CopyOnWriteArrayList

class AnimationManager(val scene: IPrimitiveElement<*>) {
    private val active = CopyOnWriteArrayList<IAnimationType>()
    private var last = System.nanoTime()

    val bool: Boolean
        get() = active.isNotEmpty()

    fun animate() {
        val b = bool
        tick()

        if (!b && !bool) return
        scene.dirty()
    }

    fun track(type: IAnimationType) {
        if (active.contains(type)) return
        active.add(type)
    }

    fun untrack(type: IAnimationType) {
        active.remove(type)
    }

    fun removeIf(predicate: (IAnimationType) -> Boolean): Boolean {
        return active.removeIf(predicate)
    }

    fun tick() {
        val now = System.nanoTime()
        val delta = ((now - last) / 1_000_000_000f).coerceIn(0f, 0.15f)
        last = now

        if (active.isEmpty()) {
            return
        }

        for (a in active) {
            if (a.advance(delta)) continue
            active.remove(a)
            a.function?.invoke()
        }
    }
}
