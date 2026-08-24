# Textures

---

## Blit Texture

```kotlin
val location = Identifier.fromNamespaceAndPath("mymod", "textures/gui/icon.png")

// basic blit
graphics.blit(
    pipeline = RenderPipelines.GUI_TEXTURED,
    location = location,
    x = 40f,
    y = 40f,
    width = 32f,
    height = 32f
)

// with custom UV coordinates
graphics.blit(
    pipeline = RenderPipelines.GUI_TEXTURED,
    location = location,
    x = 100f,
    y = 50f,
    width = 32f,
    height = 32f,
    u0 = 0.25f,
    v0 = 0.5f,
    u1 = 0.5f,
    v1 = 0.75f,
    color = 0xFFFFFFFF.toInt()
)
```

That's all for textures, not much here!
