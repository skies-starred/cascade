# Geometry Types

Cascade includes dedicated data structures for cleanly expressing geometric offsets and per-corner corner radii.

### `CascadeGeometricRadius`

```kotlin
data class CascadeGeometricRadius(
    val tl: Float, // Top-Left
    val tr: Float, // Top-Right
    val bl: Float, // Bottom-Left
    val br: Float  // Bottom-Right
)
```

**Common Constructors & Operators:**
```kotlin
// Uniform radius on all 4 corners:
val r1 = CascadeGeometricRadius(12f)
val r2 = CascadeGeometricRadius.of(8f)

// Zero radius (sharp corners):
val r3 = CascadeGeometricRadius.ZERO

// Per-corner radii (e.g. rounded top tabs):
val r4 = CascadeGeometricRadius(8f, 8f, 0f, 0f)

val r5 = r4 + 4f // Adds 4f to all corners
val r6 = r4 - 2f // Subtracts 2f from all corners
```

### `CascadeGeometricOffset`
Represents a 2D `(x, y)` displacement, primarily used for drop shadow and inner shadow positioning.

```kotlin
data class CascadeGeometricOffset(
    val x: Float,
    val y: Float
)
```

**Common Constructors & Operators:**
```kotlin
val offset1 = CascadeGeometricOffset(4f) // x = 4f, y = 4f
val offset2 = CascadeGeometricOffset(0f, 6f) // x = 0f, y = 6f
val offset3 = CascadeGeometricOffset.ZERO // x = 0f, y = 0f

val offset4 = offset2 + CascadeGeometricOffset(2f, 0f) // x + 2f
val offset5 = offset2 + 2f // x + 2f, y + 2f
```
