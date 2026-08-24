# Animation System

---

## Easing Curves (`CascadeAnimations`)

Cascade includes standard easing curves implementing the `IAnimation` interface (`apply(t: Float): Float` where `t` ranges `0f..1f`):

- `CascadeAnimations.LINEAR`
- `CascadeAnimations.EASE_IN`
- `CascadeAnimations.EASE_OUT`
- `CascadeAnimations.EASE_IN_OUT`

---

## Animating Colors (`animateColor`)

Smoothly transitions an element's ARGB color by interpolating the Alpha, Red, Green, and Blue channels independently.

```kotlin
element.animateColor(
    color1: Number,
    duration: Number, // Duration in seconds (e.g. 0.2f)
    easing: IAnimation = CascadeAnimations.LINEAR,
    function: (() -> Unit)? = null // Optional function called when animation is completed, can be used to chain animations
): T
```

---

## Animating Positions (`animatePosition`)

Smoothly animates an element to new target coordinates.

```kotlin
element.animatePosition(
    x1: Number,
    y1: Number,
    duration: Number,
    easing: IAnimation = CascadeAnimations.LINEAR
): T
```

---

## Animating Sizes (`animateSize`)

Smoothly resizes an element's width and height.

```kotlin
element.animateSize(
    w1: Number,
    h1: Number,
    duration: Number,
    easing: IAnimation = CascadeAnimations.LINEAR,
    function: (() -> Unit)? = null
): T
```

---

## Custom Properties

You can animate arbitrary values using `AnimatableFloat`:

```kotlin
val progress = AnimatableFloat(initial = 0f)

progress.animate(scene.animations!!, 100f, 1.5f, CascadeAnimations.EASE_OUT) {
    println("Progress reached 100%!")
}
```
