# Graphics Effects

---

## [1] Blur

```kotlin
graphics.blur(
    x = 50f,
    y = 50f,
    width = 280f,
    height = 160f,
    color = 0x22FFFFFF.toInt(), // tint over blur
    radius = CascadeGeometricRadius(16f),
    blur = 12f
)
```

---

## [2] Drop Shadow

```kotlin
graphics.dropShadow(
    x = 50f,
    y = 50f,
    width = 200f,
    height = 100f,
    offset = CascadeGeometricOffset(0f, 6f),
    blur = 16f,
    spread = 2f,
    color = 0x55000000,
    radius = CascadeGeometricRadius(12f)
)
```

---

## [3] Inner Shadow

```kotlin
graphics.innerShadow(
    x = 60f,
    y = 60f,
    width = 200f,
    height = 32f,
    offset = CascadeGeometricOffset(0f, 2f),
    blur = 4f,
    color = 0x66000000,
    radius = CascadeGeometricRadius(6f)
)
```

---

## [4] Scissor

```kotlin
// this method prevents Blaze3D from throwing an IllegalStateException on 26.2+ if scissor is 0x0
graphics.scissor(x = 50, y = 50, width = 200, height = 200) {
    // anything rendered here will be clipped
    graphics.roundedRectangle(20f, 20f, 400f, 400f, 0xFF45475A.toInt(), CascadeGeometricRadius(8f))
}
```
