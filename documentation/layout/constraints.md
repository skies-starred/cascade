# Constraint Layout

---

## Logic

Constraints are evaluated during the recursive `layout()` pass:
1. Sizing constraints (`ISizeConstraint`) run first to compute the element's `width` and `height`.
2. Positioning constraints (`IPositionConstraint`) run second to calculate `x` and `y`.

Both constraint interfaces receive references to the active `element` and its `parent`:
```kotlin
interface ISizeConstraint {
    fun width(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
    fun height(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
}

interface IPositionConstraint {
    fun x(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
    fun y(element: IPrimitiveElement<*>, parent: IPrimitiveElement<*>): Float
}
```

---

## [1] Positioning Constraints

### `CenterPositionConstraint`
Centers the element horizontally and vertically inside its parent container, with optional pixel offsets.

```kotlin
CenterPositionConstraint(x: Number = 0f, y: Number = 0f)
```

```kotlin
// Perfectly centered in parent
position = CenterPositionConstraint()

// Centered horizontally, 20 pixels below center
position = CenterPositionConstraint(y = 20f)
```

---

### `AlignPositionConstraint`
Aligns the element along the edges or center of its parent using `PositionAlignment` (`START`, `CENTER`, `END`).

```kotlin
AlignPositionConstraint(
    horizontal: PositionAlignment = PositionAlignment.START,
    vertical: PositionAlignment = PositionAlignment.START,
    x: Number = 0,
    y: Number = 0
)
```

```kotlin
// Top-Right corner with 12 pixels padding
position = AlignPositionConstraint(horizontal = PositionAlignment.END, vertical = PositionAlignment.START, x = -12f, y = 12f)

// Bottom-Center aligned
position = AlignPositionConstraint(horizontal = PositionAlignment.CENTER, vertical = PositionAlignment.END, y = -16f)
```

---

### `AnchorPositionConstraint`
Positions an element relative to another sibling or external element using `PositionAnchor` (`LEFT`, `RIGHT`, `ABOVE`, `BELOW`).

```kotlin
AnchorPositionConstraint(
    fn: () -> IPrimitiveElement<*>,
    anchor: PositionAnchor,
    x: Number = 0,
    y: Number = 0
)
```

```kotlin
val sidebar = roundedRectangle { /* ... */ }

// RIGHT of the sidebar with 10 pixels gap
roundedRectangle {
    position = AnchorPositionConstraint({ sidebar }, PositionAnchor.RIGHT, 10f)
}
```

---

### `FixedPositionConstraint`
Pixel dimensions. Relative to parent's top left coordinates.

```kotlin
position = FixedPositionConstraint(24f, 36f)
```

---

### `MixedPositionConstraint`
Combines two independent position constraints for the X and Y axes.

```kotlin
// Center horizontally, y = 50 pixels
position = MixedPositionConstraint(CenterPositionConstraint(), FixedPositionConstraint(0f, 50f))
```

---

## [2] Sizing Constraints

### `FillSizeConstraint`
I think the name explains it.

```kotlin
FillSizeConstraint(padding: Number = 0)
```

```kotlin
// Fill parent
size = FillSizeConstraint()

// Fill parent with 16px inner padding
size = FillSizeConstraint(padding = 16f)
```

---

### `FillAxisSizeConstraint`
Fills 100% along one axis (`HORIZONTAL` or `VERTICAL`) while maintaining a fixed dimension on the opposite axis.

```kotlin
FillAxisSizeConstraint(axis: FillAxis, fixed: Number, padding: Number = 0)
```

```kotlin
// 100% width header banner with height = 40 pixels
size = FillAxisSizeConstraint(FillAxis.HORIZONTAL, 40f, 10f)
```

---

### `PercentSizeConstraint`
Sizes the element relative to the parent's dimensions (from `0%` to `100%`).

```kotlin
PercentSizeConstraint(w: Number, h: Number)
```

```kotlin
// width = 50% and height = 100%
size = PercentSizeConstraint(50f, 100f)
```

---

### `FlexibleSizeConstraint`
May be unstable/buggy. Please let me know if you find a bug.

```kotlin
size = FlexibleSizeConstraint(padding: Number = 0)
```

---

### `FixedSizeConstraint`
Pixel dimensions.

```kotlin
size = FixedSizeConstraint(300f, 200f)
```

---

### `MixedSizeConstraint`
Combines separate constraints for width and height.

```kotlin
// Fill parent width, height at 60 pixels
size = MixedSizeConstraint(FillSizeConstraint(), FixedSizeConstraint(0f, 60f))
```
