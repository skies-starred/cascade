# Fonts

Cascade includes a custom font renderer that supports rendering fonts at any size without the pixelation and jagged edges you get with Minecraft's bitmap fonts.

It supports both TrueType fonts (rasterized at runtime), and pre-baked MSDF fonts.

---

## [1] Included Arial Font

Cascade comes pre-packaged with **Arial Regular** and **Arial Bold**. You can access them through `CascadeFonts.arial`.

### Extraction
```kotlin
CascadeFonts.extract(graphics, "Hello, World!", 40f, 40f)

val component = Component.literal("Test")
CascadeFonts.extract(graphics, component, 40f, 60f)
```

---

## [2] Text Measurement

```kotlin
val font = CascadeFonts.arial

val width = font.width("Settings", size = 16f)
val height = font.regular.height * 16f
```

---

## [3] Loading Custom Fonts

You can load your own custom fonts into Cascade using `FontRenderer`.

### Loading a TrueType Font

```kotlin
import foo.starred.cascade.graphics.font.rendering.impl.FontRenderer
import foo.starred.cascade.graphics.font.data.font.impl.TtfFontData
import foo.starred.cascade.utils.resource

object MyModFonts {
    val font: FontRenderer =
        FontRenderer(
            regular = TtfFontData(resource("/assets/mymod/pathto/font_regular.ttf")),
            bold = TtfFontData(resource("/assets/mymod/pathto/font_bold.ttf"))
        )
}
```

### Loading an MSDF Font
```kotlin
import foo.starred.cascade.graphics.font.data.font.impl.MsdfFontData

object MyModFonts {
    // if you don't have a bold png
    val font0: MsdfFontData = MsdfFontData("/assets/mymod/pathto/font")
    val font: FontRenderer = FontRenderer(regular = font0, bold = font0)

    // if you do have a bold png
    // put them like:
    // /assets/mymod/pathto/font/regular.json
    // /assets/mymod/pathto/font/regular.png
    // /assets/mymod/pathto/font/bold.json
    // /assets/mymod/pathto/font/bold.png
    val font: FontRenderer = FontRenderer("/assets/mymod/pathto/font")
}
```

---

## [4] Preloading

```kotlin
val chars = (32..126).map { it.toChar() }
MyModFonts.font.regular.preload(chars)
MyModFonts.font.bold.preload(chars)
```

---

## [5] Primitive font handling

Cascade layout primitives (such as `TextPrimitive`) support both vanilla and Cascade fonts through the `ITextWrapper` interface:

* **`VanillaTextWrapper`**
* **`CascadeTextWrapper`**

```kotlin
text {
    text = Component.literal("Test. Cascade Arial Font!")
    textSize = 18f
    wrapper = CascadeTextWrapper
}
```
