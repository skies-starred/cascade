# Screens

`CascadeScreen` extends Minecraft's `Screen` and routes input events, animations, and rendering to its root `scene` (`ContainerPrimitive`).

---

## Creating a Screen

```kotlin
class MyScreen : CascadeScreen("My Menu") {
    init {
        roundedRectangle {
            position = CenterPositionConstraint()
            size = FixedSizeConstraint(240f, 160f)
            radius = CascadeGeometricRadius(10f)

            attach(scene)
            adopt(text {
                text = Component.literal("Hello from CascadeScreen!")
                position = CenterPositionConstraint()
            })
        }
    }
}
```

---

## Opening a Screen

```kotlin
MyScreen().open()
```

---

## Render Loop

Every frame, `CascadeScreen`:
- Ticks active animations (`scene.animations?.animate()`).
- Checks `if (scene.dirty)` to recalculate layout if dirty.
- Renders the scene (`scene.render(graphics)`).
