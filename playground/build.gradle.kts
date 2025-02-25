plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version libs.versions.ksp.get()
    kotlin("plugin.parcelize")
    kotlin("plugin.serialization")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

android {
    namespace = "com.ramcosta.samples.playground"
    compileSdk = libs.versions.compileSdk.get().toIntOrNull()

    defaultConfig {
        applicationId = "com.ramcosta.samples.playground"
        minSdk = libs.versions.minSdk.get().toIntOrNull()
        targetSdk = libs.versions.targetSdk.get().toIntOrNull()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }

    // Possible Compose Destinations configs:
    ksp {
//        // Module name.
//        // It will be used as the generated sealed Destinations prefix
//        arg("compose-destinations.moduleName", "featureX")
//
//        // Can be:
//        // - "destinations":
//        // Generates the destinations and exposes a list with all of them. Doesn't generate any nav graphs
//        //
//        // - "navgraphs":
//        // Generates destinations and nav graph(s) individually without nesting any of them
//        //
//        // - "singlemodule" (default):
//        // Generates destinations and nav graphs nesting all graphs inside the default "root" one.
//        // Also creates a CoreExtensions.kt file with useful utilities for a single module case.
//        arg("compose-destinations.mode", "navgraphs")
//        // If you have a single module but you want to manually create the nav graphs, use this:
//        arg("compose-destinations.generateNavGraphs", "false")
//
//        // To change the package name where the generated files will be placed
        arg("compose-destinations.codeGenPackageName", "com.ramcosta.samples.playground.ui.screens")

        // If set to "true" will use the annotated composable's visibility in the generated Destination
        // otherwise, Destinations will always be public in this module
        arg("compose-destinations.useComposableVisibility", "true")
    }
}

kotlin {
    jvmToolchain(11)
}

dependencies {

    implementation(project(mapOf("path" to ":compose-destinations-animations")))
    implementation(project(mapOf("path" to ":playground-core")))
    ksp(project(":compose-destinations-ksp"))

    implementation(libs.androidMaterial)

    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.viewModel)

    implementation(libs.androidx.lifecycleRuntimeKtx)
    implementation(libs.androidx.activityCompose)

    implementation(libs.ktxSerializationJson)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)

    testImplementation(project(":compose-destinations-ksp"))
    testImplementation(libs.test.kotlinCompile)
    testImplementation(libs.test.kotlinCompileKsp)
}
