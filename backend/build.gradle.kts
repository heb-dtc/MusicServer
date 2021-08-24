plugins {
    id("kotlin-platform-jvm")
    kotlin("plugin.serialization")
    application
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":libsoli"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc")

    implementation("io.ktor:ktor-server-core:1.4.0")
    implementation("io.ktor:ktor-server-netty:1.4.0")
    implementation("io.ktor:ktor-serialization:1.4.0")
    implementation("io.ktor:ktor-html-builder:1.6.2")

    implementation("io.github.microutils:kotlin-logging:1.7.9")

    implementation("com.dorkbox:SystemTray:3.17")

    implementation("net.java.dev.jna:jna:5.4.0")

    implementation("org.freedesktop.gstreamer:gst1-java-core:1.1.0")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.3.70")
    testImplementation(kotlin("test-junit5"))
}

group = "net.hebus"
description = "musicserver"

application {
    mainClass.set("net.hebus.ApplicationKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
