package foo.starred.cascade.utils

import foo.starred.cascade.graphics.font.CascadeFonts
import java.io.InputStream

fun resource(path: String): InputStream {
    return CascadeFonts::class.java.getResourceAsStream(path) ?: error("Could not find resource: $path")
}