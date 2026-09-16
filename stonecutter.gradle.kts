plugins {
    id("dev.kikugie.stonecutter")
    alias(libs.plugins.loom) apply false
}

stonecutter active "26.1"

val mod = extensions.getByType<VersionCatalogsExtension>().named("mod")

stonecutter parameters {
    swaps["mod_version"] = "\"${mod("version")}\""
    swaps["mod_id"] = "\"${mod("id")}\""
    swaps["mod_name"] = "\"${mod("name")}\""
    swaps["minecraft"] = "\"${node.metadata.version}\""

    swaps["layout"] = if (current.parsed >= "26.3") "layout(location = $1) $2 " else "$2 "

    replacements {
        string(current.parsed >= "26.2") {
            replace("com.mojang.blaze3d.vertex.VertexFormatElement", "com.mojang.blaze3d.GpuFormat")
            replace("withVertexFormat(VERTEX_FORMAT, VertexFormat.Mode.QUADS)", "withVertexBinding(0, VERTEX_FORMAT)")
        }

        string(current.parsed >= "26.3") {
            replace("#moj_import", "#include")

            replace("com.mojang.blaze3d.pipeline.RenderPipeline", "com.mojang.renderpearl.api.pipeline.RenderPipeline")
            replace("com.mojang.blaze3d.pipeline.ColorTargetState", "com.mojang.renderpearl.api.pipeline.ColorTargetState")
            replace("com.mojang.blaze3d.pipeline.BindGroupLayout", "com.mojang.renderpearl.api.pipeline.BindGroupLayout")
            replace("com.mojang.blaze3d.pipeline.UniformType", "com.mojang.renderpearl.api.pipeline.UniformType")
            replace("com.mojang.blaze3d.vertex.VertexFormat", "com.mojang.renderpearl.api.vertex.VertexFormat")
            replace("com.mojang.blaze3d.GpuFormat", "com.mojang.renderpearl.api.GpuFormat")
            replace("com.mojang.blaze3d.buffers", "com.mojang.renderpearl.api.buffers")
            replace("com.mojang.blaze3d.textures", "com.mojang.renderpearl.api.textures")
            replace("com.mojang.blaze3d.systems.CommandEncoder", "com.mojang.renderpearl.api.commands.CommandEncoder")
            replace("com.mojang.blaze3d.systems.RenderPass", "com.mojang.renderpearl.api.commands.RenderPass")
        }
    }
}

stonecutter handlers {
    inherit("vsh", "glsl")
}

operator fun VersionCatalog.invoke(name: String): String {
    return findVersion(name).get().requiredVersion
}

