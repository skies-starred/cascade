# Custom Elements & Primitives

You can create your own primitives (by extending `IPrimitiveElement<T>`) or reusable composite widgets.

---

##  Primitives

```kotlin
package com.example.mod.gui.primitives

import foo.starred.cascade.primitives.base.impl.IPrimitiveElement
import net.minecraft.client.gui.GuiGraphicsExtractor

open class SomethingPrimitive : IPrimitiveElement<SomethingPrimitive>() {
    override var x: Float = 0f
    override var y: Float = 0f
    override var width: Float = 32f
    override var height: Float = 32f
    override var color: Int = -1

    override fun draw(graphics: GuiGraphicsExtractor) {
        // draw here, called every frame
    }

    companion object {
        val NONE = SomethingPrimitive()

        inline fun something(block: SomethingPrimitive.() -> Unit): SomethingPrimitive {
            return SomethingPrimitive().apply(block)
        }
    }
}
```

---

## Reusable Components

For reusable components, you can extend an existing primitive like `RoundedRectanglePrimitive` or `ContainerPrimitive`, or choose to extend IPrimitiveElement to do it from scratch.

```kotlin
open class SimpleButton(label: String, function: () -> Unit) : RoundedRectanglePrimitive() {
    init {
        size = FixedSizeConstraint(120f, 32f)
        color = Int.MIN_VALUE
        radius = CascadeGeometricRadius(6f)

        adopt(text {
            position = CenterPositionConstraint()
            text = Component.literal(label)
            center = true
        })

        on<MouseEvent.Press> {
            if (button != 0) return@on

            function()
            cancel()
        }
    }

    companion object {
        inline fun simpleButton(label: String, noinline function: () -> Unit, block: SimpleButton.() -> Unit = {}): SimpleButton {
            return SimpleButton(label, function).apply(block)
        }
    }
}
```
