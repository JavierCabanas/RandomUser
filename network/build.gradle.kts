plugins {
    id("com.android.library")
//    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("plugin.serialization") version Versions.kotlin
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(libraries.di.daggerHilt)
    kapt(libraries.di.daggerHiltCompiler)

    testImplementation(test.jUnit)
    androidTestImplementation(androidTest.espressoCore)
}
