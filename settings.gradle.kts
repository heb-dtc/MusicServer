pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "Soli"
include(":androidApp")
include(":backend")
include(":desktop")
include(":libsoli")