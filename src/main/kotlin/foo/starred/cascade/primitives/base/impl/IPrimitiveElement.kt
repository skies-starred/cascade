@file:Suppress("Unused", "Unchecked_cast", "PropertyName")

package foo.starred.cascade.primitives.base.impl

import com.mojang.blaze3d.platform.cursor.CursorType
import foo.starred.cascade.animation.manager.AnimationManager
import foo.starred.cascade.constraints.base.IPositionConstraint
import foo.starred.cascade.constraints.base.ISizeConstraint
import foo.starred.cascade.effects.base.IEffect
import foo.starred.cascade.events.base.UIEvent
import foo.starred.cascade.events.impl.FocusEvent
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset
import foo.starred.cascade.primitives.base.abstract.AbstractPrimitiveExtensions
import foo.starred.cascade.primitives.base.interfaces.*
import java.util.concurrent.CopyOnWriteArrayList

abstract class IPrimitiveElement<T : IPrimitiveElement<T>> : AbstractPrimitiveExtensions<T>(), IPrimitiveChildren<T>, IPrimitiveConstrainable<T>, IPrimitiveCursors<T>, IPrimitiveEffects<T>, IPrimitiveEvents<T>, IPrimitiveFindable<T>, IPrimitiveInteractable<T>, IPrimitiveLayoutResolver<T>, IPrimitiveRenderable<T> {
    internal var _root: IPrimitiveElement<*>? = null

    abstract var x: Float
    abstract var y: Float
    abstract var width: Float
    abstract var height: Float
    abstract var color: CascadeGeometricColor

    override val effects: CopyOnWriteArrayList<IEffect> = CopyOnWriteArrayList()
    override val children: CopyOnWriteArrayList<IPrimitiveElement<*>> = CopyOnWriteArrayList()
    override val listeners: MutableMap<Class<out UIEvent>, MutableList<UIEvent.() -> Unit>> = mutableMapOf()

    override val root: IPrimitiveElement<*>
        get() {
            val r = _root
            if (r != null && r.parent == null) return r
            return generateSequence(this as IPrimitiveElement<*>) { it.parent }.last().also { _root = it }
        }

    override val self: T
        get() = this as T

    override var parent: IPrimitiveElement<*>? = null
        set(value) {
            field = value
            _root = null
            root.dirty()
        }

    override var size: ISizeConstraint? = null
        set(value) {
            field = value
            root.dirty()
        }

    override var position: IPositionConstraint? = null
        set(value) {
            field = value
            root.dirty()
        }

    override var offset: CascadeGeometricOffset = CascadeGeometricOffset.ZERO
        set(value) {
            if (field == value) return
            field = value
            root.dirty()
        }

    override var visible: Boolean = true
        set(value) {
            if (field == value) return
            field = value
            root.dirty()
        }

    override var focused: IPrimitiveElement<*>? = null
        set(value) {
            if (field == value) return
            val field0 = field
            field = value

            field0?.post(FocusEvent.Lose(field0))
            value?.post(FocusEvent.Gain(value))
        }

    override var cursor: CursorType? = null
    override var dirty: Boolean = false
    override var interact: Boolean = true
    override var hovered: Boolean = false
    override var unfocus: Boolean = true

    var animations: AnimationManager? = null

    var scale: Float = 1f
        get() = if (this === root) field else root.scale

    var mouseX: Float = 0f
        get() = if (this === root) field else root.mouseX

    var mouseY: Float = 0f
        get() = if (this === root) field else root.mouseY
}
