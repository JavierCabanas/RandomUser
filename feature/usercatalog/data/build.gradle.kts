import libraries.coroutines.android

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("plugin.serialization") version Versions.kotlin
}

android {
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        compileSdkVersion(Versions.compileSdk)
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

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
    implementation(project(":feature:usercatalog:domain"))
    implementation(project(":network"))
    implementation(libraries.kotlin)
    implementation(libraries.androidX.appcompat)
    implementation(libraries.androidX.core)
    implementation(libraries.network.retrofit)
    implementation(libraries.network.retrofitConverter)
    implementation(libraries.serialization)

    testImplementation(test.jUnit)
    testImplementation(libraries.network.mockWebServer)
    androidTestImplementation(androidTest.espresso)
}
