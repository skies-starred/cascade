# Shapes

---

## [1] Rectangle
```kotlin
graphics.rectangle(
    x = 20f,
    y = 20f,
    width = 300f,
    height = 200f
)
```

---

## [2] Rounded Rectangle

```kotlin
// all corners 12px
graphics.roundedRectangle(
    40f,
    40f,
    240f,
    120f,
    -1,
    CascadeGeometricRadius(12f)
)

// per-corner radii
graphics.roundedRectangle(
    40f,
    40f,
    240f,
    40f,
    -1,
    CascadeGeometricRadius(12f, 12f, 0f, 0f)
)
```

---

## [3] Hollow Rectangle
Border outline with rounded corner support.

```kotlin
graphics.hollowRectangle(
    40f,
    40f,
    240f,
    120f,
    thickness = 2f,
    radius = CascadeGeometricRadius(12f)
)
```

---

## [4] Gradient Rectangle

```kotlin
// 4-corner gradient
graphics.gradientRectangle(
    50f,
    50f,
    200f,
    150f,
    tl = 0xFFFF5555.toInt(),
    tr = 0xFF55FFFF.toInt(),
    bl = 0xFF55FF55.toInt(),
    br = 0xFFFFAA00.toInt()
)

// 2-color linear gradient (vertical or horizontal)
graphics.gradientRectangle(
    0f,
    0f,
    400f,
    300f,
    from = 0xFFF38BA8.toInt(),
    to = 0xFF1E1E2E.toInt(),
    vertical = true
)
```

---

## [5] Circle
`(x, y)` is the center point.

```kotlin
graphics.circle(
    x = 100f,
    y = 100f,
    radius = 24f
)
```

---

## [6] Arc

```kotlin
// 75% circle with rounded caps
graphics.arc(
    x = 150f,
    y = 150f,
    radius0 = 28f,
    radius1 = 36f,
    angle0 = 0f,
    angle1 = 270f,
    rounded = true
)
```

---

## [7] Ring

```kotlin
graphics.ring(
    x = 150f,
    y = 150f,
    radius0 = 34f,
    radius1 = 36f
)
```

---

## [8] Triangle

```kotlin
graphics.triangle(
    x0 = 100f,
    y0 = 50f,
    x1 = 110f,
    y1 = 60f,
    x2 = 120f,
    y2 = 50f
)
```

---

## [9] Stroke (Line)

```kotlin
graphics.stroke(
    x1 = 20f,
    y1 = 20f,
    x2 = 180f,
    y2 = 80f,
    color = -1,
    thickness = 2.5f
)
```
