@file:Suppress("Unused")

package foo.starred.cascade.wrappers.svg.impl

import com.github.weisj.jsvg.parser.SVGLoader
import foo.starred.cascade.Cascade.client
import foo.starred.cascade.wrappers.svg.utils.CascadeSVGRasterizer
import net.minecraft.resources.Identifier
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap

object CascadeSVG {
    private val cache = ConcurrentHashMap<String, Identifier>()

    val available: Boolean by lazy {
        runCatching { SVGLoader() }.isSuccess
    }

    fun load(id: Identifier, width: Int, height: Int): Identifier {
        return load(id, width, height) {
            client.resourceManager.getResource(id).orElseThrow { IllegalArgumentException("SVG not found: $id") }.open()
        }
    }

    fun load(svg: String, id: Identifier, width: Int, height: Int): Identifier {
        return load(id, width, height) { svg.byteInputStream() }
    }

    fun load(stream: InputStream, id: Identifier, width: Int, height: Int): Identifier {
        return load(id, width, height) { stream }
    }

    fun remove(scale: Int) {
        if (cache.isEmpty()) return
        val suffix = "s$scale"

        cache.entries.removeIf { kv ->
            !kv.key.endsWith(suffix).also { if (it) client.textureManager.release(kv.value) }
        }
    }

    fun remove(id: Identifier) {
        if (cache.isEmpty()) return
        val prefix = "$id@"

        cache.entries.removeIf { kv ->
            (kv.value == id || kv.key.startsWith(prefix)).also { if (it) client.textureManager.release(kv.value) }
        }
    }

    fun remove() {
        if (cache.isEmpty()) return

        cache.values.removeIf { id ->
            client.textureManager.release(id)
            true
        }
    }

    private fun load(id: Identifier, width: Int, height: Int, source: () -> InputStream): Identifier {
        check(available) { "Could not find JSVG on classpath!" }

        val scale = client.window.guiScale.coerceAtLeast(1)
        val key = "${id}@${width}x${height}s$scale"

        return cache.getOrPut(key) {
            source().use {
                val width = width * scale
                val height = height * scale

                CascadeSVGRasterizer.draw(it, path(id, width, height), width, height)
            }
        }
    }

    private fun path(id: Identifier, width: Int, height: Int): Identifier {
        val clean = id.path.removeSuffix(".svg").trimStart('/')
        return if (clean.startsWith("svg/")) id.withPath("$clean/${width}x$height") else id.withPath("svg/$clean/${width}x$height")
    }
}
