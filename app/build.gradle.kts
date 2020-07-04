plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}
android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId = "me.javicabanas.randomuser"
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
    implementation(libraries.kotlin)
    implementation(libraries.androidX.appcompat)
    implementation(libraries.androidX.core)

    testImplementation(test.jUnit)

    androidTestImplementation(androidTest.jUnit)
    androidTestImplementation(androidTest.espresso)
}
