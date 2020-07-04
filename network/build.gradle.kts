plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("plugin.serialization") version Versions.kotlin
}

@Suppress("MagicNumber")
android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libraries.kotlin)
    implementation(libraries.androidX.appcompat)
    implementation(libraries.androidX.core)
    implementation(libraries.network.okhttp)
    implementation(libraries.network.logginIntercepor)
    implementation(libraries.network.retrofit)
    implementation(libraries.network.retrofitConverter)
    implementation(libraries.serialization)

    testImplementation(test.jUnit)
    androidTestImplementation(androidTest.espresso)
}
