package foo.starred.cascade.graphics.states.pipeline.render.data

import com.mojang.blaze3d.pipeline.ColorTargetState
import com.mojang.blaze3d.vertex.VertexFormat
import foo.starred.cascade.graphics.states.pipeline.sampler.builder.CascadeSamplerBuilder

class CascadeRenderPipelineData {
    lateinit var location: String

    val sampler: CascadeSamplerBuilder = CascadeSamplerBuilder()

    var shader: String? = null
    var vertexShader: String? = null
    var fragmentShader: String? = null

    var vertexFormat: VertexFormat? = null
    var colorTargetState: ColorTargetState? = null
}
