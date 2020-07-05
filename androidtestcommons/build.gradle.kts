plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
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
    implementation(androidTest.jUnit)
    implementation(androidTest.runner)
    implementation(libraries.di.daggerHilt)
    implementation(libraries.ui.recyclerview)
    implementation(androidTest.espressoContrib)
    kapt(libraries.di.daggerHiltCompiler)
    implementation(test.daggerHilt)
    implementation(libraries.di.lifecycleHilt)
    kapt(libraries.di.lifecycleHiltCompiler)
    implementation(androidTest.espressoIntents)



    testImplementation(test.jUnit)
    testImplementation(test.mockk)
    androidTestImplementation(androidTest.espressoCore)
    androidTestImplementation(test.mockk)
}
