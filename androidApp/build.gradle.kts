plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    val composeVersion = "1.1.0"
    implementation(project(":libsoli"))

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.core:core-ktx:1.2.0")

    implementation("io.coil-kt:coil-compose:1.3.0")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha02")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

    implementation("com.google.android.material:material:1.4.0")

    implementation("com.google.android.exoplayer:exoplayer:2.14.2")

    implementation("androidx.navigation:navigation-compose:2.4.0-alpha06")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-service:2.2.0")

    implementation("io.ktor:ktor-client-android:1.5.4")
    implementation("io.ktor:ktor-client-okhttp:1.4.1")
    implementation("io.ktor:ktor-client-cio:1.4.1")
    implementation("io.ktor:ktor-client-serialization-jvm:1.4.1")
    implementation("io.ktor:ktor-client-logging-jvm:1.5.0")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.heb.soli.android"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    packagingOptions {
        // The Rome library JARs embed some internal utils libraries in nested JARs.
        // We don't need them so we exclude them in the final package.
        resources.excludes.add("/*.jar")
    }

    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }

    composeOptions {
        //kotlinCompilerVersion "1.5.20"
        kotlinCompilerExtensionVersion = "1.1.0"
    }
}