# Cascade

A UI and font rendering engine for Minecraft.

## Features

- **Fonts:** Load `.ttf` files or use pre-baked MSDF fonts.
- A Kotlin-first constraint-based layout builder
- Rounded rectangles, blur, circles, arcs, rings, triangles, and more!

## Documentation

Check it out here: [Click me!](./documentation)

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

Latest cascade version: `034` / Usually updated, check commits for latest ``bump(version): <version>`` commit if not!
\
Minecraft versions: `1.21.11`, `26.1`,  `26.2`
\
Cascade will aim to support the latest three Minecraft versions.
