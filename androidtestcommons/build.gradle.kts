plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
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
}

dependencies {
    implementation(project(":core"))
    implementation(libraries.kotlin)
    implementation(libraries.androidX.appcompat)
    implementation(libraries.androidX.core)
    implementation(libraries.androidX.viewModel)
    implementation(libraries.androidX.viewModelScope)
    implementation(libraries.coroutines.coroutinesTest)
    implementation(test.jUnit)

    testImplementation(test.jUnit)
    androidTestImplementation(androidTest.espresso)
}