# Primitives

Primitives are the core elements in Cascade.
Each element extends `IPrimitiveElement<T>` and provides a companion builder function.

---

## Common Properties
All primitives have:
* `x`, `y`, `width`, `height`, `color`
* `position: IPositionConstraint?`
* `size: ISizeConstraint?`
* `visible: Boolean`
* `interact: Boolean` (set `false` to pass clicks through)
* `hovered: Boolean`
* `effects`: List of effects
* `children`: List of children
* `adopt(child)`, `disown(child)`, `attach()`, `detach()`,
* `on<T> { ... }`: Registers an event listener

---

## [1] `ContainerPrimitive`
Invisible container.

```kotlin
container {
    position = CenterPositionConstraint()
    size = PercentSizeConstraint(80f, 80f)

    adopt(roundedRectangle { /* ... */ })
}
```

---

## [2] `RectanglePrimitive`
Solid rectangle quad.

```kotlin
rectangle {
    width = 200f
    height = 4f
}
```

---

## [3] `RoundedRectanglePrimitive`

```kotlin
roundedRectangle {
    size = FixedSizeConstraint(240f, 140f)
    radius = CascadeGeometricRadius(12f)
}
```

---

## [4] `TextPrimitive`
Renders strings or Minecraft `Component`s.

```kotlin
text {
    text = Component.literal("Hello World")
    textSize = 14f
    shadow = true
    center = false
    wrapper = CascadeTextWrapper // or VanillaTextWrapper
}
```

---

## [5] `ImagePrimitive`
Renders textures and sprites.

```kotlin
image {
    location = Identifier.fromNamespaceAndPath("mymod", "textures/gui/icon.png")
    width = 32f
    height = 32f
    rotation = 0f
    sprite = false
}
```

---

## [6] `ItemPrimitive`
Renders an `ItemStack`.

```kotlin
item {
    item = Items.DIAMOND_SWORD.defaultInstance
    position = FixedPositionConstraint(10f, 10f)
}
```

---

## [7] `EntityPrimitive`
Renders a living entity.

```kotlin
entity {
    entity = Cascade.client.player
    size = FixedSizeConstraint(120f, 180f)
    cursor = true // looks at the mouse cursor
    items = true  // shows held items and armor
}
```

---

## [8] `ScrollablePrimitive`
Scrollable container that automatically handles mouse scrolling and scissors child content.

```kotlin
scrollable {
    size = FixedSizeConstraint(240f, 300f)

    for (i in 1..20) {
        adopt(roundedRectangle {
            position = FixedPositionConstraint(0f, (i - 1) * 35f)
            size = FixedSizeConstraint(220f, 30f)
            color = 0xFF313244.toInt()
        })
    }
}
```

---

## [9] `CirclePrimitive`

```kotlin
circle {
    radius = CascadeGeometricRadius(20f)
}
```

---

## [10] `ArcPrimitive`
```kotlin
arc {
    radius0 = 24f
    radius1 = 30f
    angle0 = 0f
    angle1 = 270f
    rounded = true
}
```

---

## [11] `TrianglePrimitive`

```kotlin
triangle {
    p0 = Vector2f(0f, 0f)
    p1 = Vector2f(10f, 5f)
    p2 = Vector2f(0f, 10f)
}
```

---

## [12] `StrokePrimitive`

```kotlin
stroke {
    x2 = 200f
    y2 = 0f
    thickness = 1.5f
}
```

---

## [13] `BlurPrimitive`

```kotlin
blur {
    size = FillSizeConstraint()
    blur = 12f
    radius = CascadeGeometricRadius(12f)
}
```

---

## [14] `RenderStatePrimitive`

```kotlin
renderState {
    state = myCustomGuiElementRenderState
    ascend = true // advances to next stratum layer if needed
}
```
