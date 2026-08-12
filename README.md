# Cascade

A UI and font rendering engine for Minecraft.

## Features

- **Fonts:** Load `.ttf` files or use pre-baked MSDF fonts.

### Primitives

- `ArcPrimitive`
- `ContainerPrimitive`
- `CirclePrimitive`
- `ImagePrimitive`
- `ItemPrimitive`
- `LinePrimitive`
- `RectanglePrimitive`
- `RoundedRectanglePrimitive`
- `ScrollablePrimitive`
- `TextPrimitive`
- `TrianglePrimitive`
- `RenderStatePrimitive`

### Render states

- `ArcRenderState`
- `CircleRenderState`
- `LineRenderState`
- `RectangleRenderState`
- `RoundedRectangleRenderState`
- `TextureRenderState`
- `TriangleRenderState`

## Usage

### Including in dependencies

```kotlin
repositories {
    maven("https://maven.starred.foo/releases")
}

dependencies {
    // if on 1.21.11 use modImplementation
    implementation("foo.starred:cascade:<version>+<minecraft>")
}
```

Latest cascade version: `012`
\
Minecraft versions: `1.21.11`, `26.1`,  `26.2`
\
Cascade will aim to support the latest three Minecraft versions.