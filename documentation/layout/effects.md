# Effects

---

## Logic

Every primitive implements `IPrimitiveEffects<T>` and maintains a list of `effects: CopyOnWriteArrayList<IEffect>`.

When an element with attached effects renders:
1. **`before(...)`:** Runs before the element's `draw()` method.
2. **`draw(...)`:** The element draws itself.
3. **`after(...)`:** Runs after the element's `draw()` method.
4. **Children:** Elements render on top of the parent and its effects.

---

## Attaching Effects

```kotlin
val card = roundedRectangle {
    size = FixedSizeConstraint(260f, 160f)
    radius = CascadeGeometricRadius(12f)

    effect(DropShadowEffect {
        blur = 16f
        spread = 2f
        color = 0x66000000
    })

    effect(BackdropBlurEffect {
        blur = 10f
    })

    effect(OutlineEffect {
        width = 1.5f
        color = 0x44FFFFFF.toInt()
    })
}
```

---

## Included Effects

### [1] `DropShadowEffect`
Draws a drop shadow before an element renders.

| Property | Default                       | Description                                                                |
|----------|-------------------------------|----------------------------------------------------------------------------|
| `offset` | `CascadeGeometricOffset.ZERO` | Directional displacement of the shadow.                                    |
| `blur`   | `8f`                          | Penumbra blur radius.                                                      |
| `spread` | `0f`                          | Shape expansion before blurring.                                           |
| `color`  | `0x40000000`                  | Shadow color.                                                              |
| `radius` | Inherited from element        | Custom corner radii (defaults to element's radius if `IPrimitiveRounded`). |

```kotlin
effect(DropShadowEffect {
    offset = CascadeGeometricOffset(0f, 6f)
    blur = 16f
    color = 0x55000000
})
```

---

### [2] `OuterGlowEffect`
Renders a glow-like effect before an element renders.

| Property | Default                | Description                      |
|----------|------------------------|----------------------------------|
| `blur`   | `12f`                  | Glow dispersion radius.          |
| `spread` | `0f`                   | Shape expansion before blurring. |
| `color`  | `0x6600AAFF`           | Glow color.                      |
| `radius` | Inherited from element | Corner radii.                    |

```kotlin
effect(OuterGlowEffect {
    blur = 14f
    spread = 1f
    color = 0x88F38BA8.toInt() // pink glow
})
```

---

###  [3] `BackdropBlurEffect`
Blurs the contents behind the element.

| Property | Default                | Description               |
|----------|------------------------|---------------------------|
| `blur`   | `8f`                   | Intensity of the blur.    |
| `color`  | `0`                    | Color tint over the blur. |
| `radius` | Inherited from element | Corner radii.             |

```kotlin
effect(BackdropBlurEffect {
    blur = 12f
    color = 0x22000000 // dark tint
})
```

---

### [4] `InnerShadowEffect`
Renders an inset shadow around the inside perimeter of the element during the `after()` pass.

| Property | Default                       | Description                   |
|----------|-------------------------------|-------------------------------|
| `offset` | `CascadeGeometricOffset.ZERO` | Inset displacement direction. |
| `blur`   | `6f`                          | Inset blur softness.          |
| `color`  | `0x40000000`                  | Inner shadow color.           |
| `radius` | Inherited from element        | Corner radii.                 |

```kotlin
effect(InnerShadowEffect {
    offset = CascadeGeometricOffset(0f, 2f)
    blur = 6f
    color = 0x66000000
})
```

---

### [5] `OutlineEffect`
Draws a crisp border around the element during the `after()` pass.

| Property | Default                | Description                 |
|----------|------------------------|-----------------------------|
| `width`  | `1f`                   | Border thickness in pixels. |
| `color`  | `-1`                   | Border color.               |
| `inset`  | `true`                 | Name explains it I think.   |
| `radius` | Inherited from element | Corner radii.               |

```kotlin
effect(OutlineEffect {
    width = 2f
    color = 0xFF89B4FA.toInt()
    inset = true
})
```

---

## Custom Effects

You can create your own custom effects by implementing `IEffect`:

```kotlin
class CustomEffect : IEffect() {
    override fun before(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {
        // Custom drawing logic under the element
    }

    override fun after(element: IPrimitiveElement<*>, graphics: GuiGraphicsExtractor, pose: Matrix3x2f, scissor: ScreenRectangle?) {
        // Custom drawing logic over the element
    }
}
```
