# Events

Register event listeners on any primitive using `on<T> { ... }`.

---

## Mouse Events

| Event                   | Description            | Properties              |
|-------------------------|------------------------|-------------------------|
| `MouseEvent.Press`      | Mouse button pressed   | `x, y, button, element` |
| `MouseEvent.Release`    | Mouse button released  | `x, y, button, element` |
| `MouseEvent.Scroll`     | Mouse wheel scrolled   | `x, y, amount, element` |
| `MouseEvent.Move.Enter` | Cursor entered element | `x, y, element`         |
| `MouseEvent.Move.Exit`  | Cursor left element    | `x, y, element`         |
| `MouseEvent.Move.Any`   | Cursor moved           | `x, y, element`         |

### Example: Click & Hover
```kotlin
val button = roundedRectangle {
    size = FixedSizeConstraint(120f, 36f)
    radius = CascadeGeometricRadius(6f)

    on<MouseEvent.Move.Enter> {
        // entered
    }

    on<MouseEvent.Move.Exit> {
        // exited
    }

    on<MouseEvent.Press> {
        println("Clicked!")
        cancel() // consumes event
    }
}
```

---

## Keyboard Events

Dispatched to the currently focused element (`scene.focused`).

| Event              | Description     | Properties |
|--------------------|-----------------|------------|
| `KeyEvent.Press`   | Key pressed     | `key`      |
| `KeyEvent.Release` | Key released    | `key`      |
| `KeyEvent.Type`    | Character typed | `char`     |

```kotlin
on<KeyEvent.Type> {
    println("Typed: $char")
}
```

---

## Focus Events

| Event             | Description          |
|-------------------|----------------------|
| `FocusEvent.Gain` | Element gained focus |
| `FocusEvent.Lose` | Element lost focus   |

To prevent clicking outside from clearing focus, set `unfocus = false`.
