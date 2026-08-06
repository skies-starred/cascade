plugins {
    id("dev.kikugie.stonecutter")
    alias(libs.plugins.loom) apply false
    alias(libs.plugins.loom.obf) apply false
}

stonecutter active "26.1"

stonecutter parameters {
    swaps["mod_version"] = "\"" + property("mod.version") + "\""
    swaps["mod_id"] = "\"" + property("mod.id") + "\""
    swaps["mod_name"] = "\"" + property("mod.name") + "\""
    swaps["minecraft"] = "\"" + node.metadata.version + "\""

    replacements {
        string(current.parsed >= "26.1") {
            replace("ClientCommandManager", "ClientCommands")
            replace("GuiGraphics", "GuiGraphicsExtractor")
        }
    }
}