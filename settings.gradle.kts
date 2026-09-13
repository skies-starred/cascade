import dev.kikugie.stonecutter.settings.tree.TreeBuilder

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/snapshots")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9"
}

stonecutter.create(rootProject) {
    setup("26.1", "26.2", "26.3")
    vcsVersion = "26.1"
}

fun TreeBuilder.setup(vararg versions: String) {
    versions(*versions)

    dependencyResolutionManagement.versionCatalogs {
        create("mod") {
            from(files("gradle/mod.versions.toml"))
        }

        for (ver in versions) {
            val toml = file("gradle/${ver.replace(".", "-")}.versions.toml")
                .takeIf { it.exists() } ?: file("gradle/$ver.versions.toml")

            create("libs${ver.replace(".", "")}") {
                from(files(toml))
            }
        }
    }
}
