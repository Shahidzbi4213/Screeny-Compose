import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.ksp)
    id("kotlinx-serialization")


}

android {
    signingConfigs {
        create("release") {
            storeFile = file("/home/shahid-iqbal/AndroidStudioProjects/Screeny/screeny.jks")
            storePassword = "screeny"
            keyAlias = "key0"
            keyPassword = "screeny"
        }
    }
    namespace = "com.shahid.iqbal.screeny"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.shahid.iqbal.screeny"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }

        val prop = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "local.properties")))
        }
        val apiKey = prop.getProperty("api_key")
        buildConfigField("String", "API_KEY", apiKey)

        resourceConfigurations += mutableSetOf(
            "en", "ar", "ru", "in", "bn", "hi", "uk", "vi", "ko", "ja", "zh", "sv",
            "pl", "ms", "fr", "it", "fa", "tr", "th", "pt", "es", "de",
            "nl", "ta", "cs", "ur"
        )

    }

    buildTypes {

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions { jvmTarget = "17" }

    buildFeatures {
        compose = true
        buildConfig = true
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //lifecycle-compose
    implementation(libs.androidx.lifecycle.runtime.compose.android)

    //Coil Image Loading Library
    implementation(libs.coil.compose)

    //Koin As Dependency Injection
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    //Ktor for Network Requests
    implementation(libs.bundles.ktor.client)

    //Room Database
    implementation(libs.bundles.room.db)
    ksp(libs.room.ksp)

    //Paging Library
    implementation(libs.pagging.library)

    //Navigation Library
    implementation(libs.compose.navigation)


}