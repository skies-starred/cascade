package foo.starred.cascade.wrappers.svg.utils

import com.github.weisj.jsvg.SVGDocument
import com.github.weisj.jsvg.parser.LoaderContext
import com.github.weisj.jsvg.parser.SVGLoader
import com.github.weisj.jsvg.view.ViewBox
import com.mojang.blaze3d.platform.NativeImage
import foo.starred.cascade.Cascade.client
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.resources.Identifier
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import java.io.InputStream
import java.net.URI

object CascadeSVGRasterizer {
    private val loader by lazy { SVGLoader() }
    private val context by lazy { LoaderContext.builder().build() }

    fun draw(stream: InputStream, id: Identifier, width: Int, height: Int): Identifier {
        val svg = loader.load(stream, URI(""), context) ?: throw IllegalArgumentException("Failed to parse SVG: $id")

        client.textureManager.register(id, DynamicTexture({ id.toString() }, rasterize(svg, width, height)))
        return id
    }

    private fun rasterize(svg: SVGDocument, width: Int, height: Int): NativeImage {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val graphics = image.createGraphics()

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)

        svg.render(null, graphics, ViewBox(0f, 0f, width.toFloat(), height.toFloat()))
        graphics.dispose()

        val image1 = NativeImage(NativeImage.Format.RGBA, width, height, false)
        val pixels = (image.raster.dataBuffer as DataBufferInt).data
        var i = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                image1.setPixel(x, y, pixels[i++])
            }
        }

        return image1
    }
}
