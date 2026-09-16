package foo.starred.cascade.graphics.states.pipeline.render.builder

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexFormat
import foo.starred.cascade.graphics.states.pipeline.render.data.CascadeRenderPipelineData
import foo.starred.cascade.graphics.states.pipeline.vertex.impl.CascadeVertexFormats
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.resources.Identifier

class CascadeRenderPipelineBuilder(
    var snippet: RenderPipeline.Snippet = RenderPipelines.GUI_SNIPPET
) {
    var data: CascadeRenderPipelineData = CascadeRenderPipelineData()
    var configure: (RenderPipeline.Builder.() -> Unit)? = null

    fun setupBuilder(block: CascadeRenderPipelineBuilder.() -> Unit): CascadeRenderPipelineBuilder {
        return apply(block)
    }

    fun setupPipeline(block: RenderPipeline.Builder.() -> Unit) {
        configure = block
    }

    fun build(): RenderPipeline {
        val location = identifier(data.location)
        val builder = RenderPipeline.builder(snippet).withLocation(location)

        val vertex = data.vertexShader ?: data.shader
        if (vertex != null) {
            builder.withVertexShader(identifier(vertex))
        }

        val fragment = data.fragmentShader ?: data.shader
        if (fragment != null) {
            builder.withFragmentShader(identifier(fragment))
        }

        data.colorTargetState?.let {
            builder.withColorTargetState(it)
        }

        data.vertexFormat?.let {
            //? if >= 26.2 {
            /*builder.withVertexBinding(0, it)
            *///? } else {
            builder.withVertexFormat(it, VertexFormat.Mode.QUADS)
            //? }
        }

        data.sampler.build(builder)
        configure?.invoke(builder)
        return RenderPipelines.register(builder.build())
    }

    companion object {
        fun cascadeRenderPipeline(
            id: String,
            format: CascadeVertexFormats? = null,
            shader: String? = "core/shapes/$id/$id",
            snippet: RenderPipeline.Snippet = RenderPipelines.GUI_SNIPPET,
            setup: (CascadeRenderPipelineBuilder.() -> Unit)? = null
        ): RenderPipeline {
            val builder = CascadeRenderPipelineBuilder(snippet).apply {
                data.location = id
                data.shader = shader
                data.vertexFormat = format?.format
            }

            setup?.invoke(builder)
            return builder.build()
        }

        private fun identifier(path: String): Identifier {
            return if (':' in path) Identifier.parse(path) else Identifier.fromNamespaceAndPath("cascade", path)
        }
    }
}
