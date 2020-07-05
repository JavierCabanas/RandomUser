plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
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

        testInstrumentationRunner =
            "me.javicabanas.randomuser.androidtestcommons.runner.HiltTestRunner"
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
    implementation(project(":feature:usercatalog:domain"))
    implementation(project(":androidcommons"))
    implementation(libraries.kotlin)
    implementation(libraries.androidX.appcompat)
    implementation(libraries.androidX.core)
    implementation(libraries.androidX.viewModel)
    implementation(libraries.androidX.viewModelScope)
    implementation("androidx.activity:activity-ktx:1.1.0")
    implementation(libraries.di.daggerHilt)
    kapt(libraries.di.daggerHiltCompiler)
    implementation(libraries.di.lifecycleHilt)
    kapt(libraries.di.lifecycleHiltCompiler)
    implementation(libraries.ui.material)
    implementation(libraries.ui.constraintLayout)
    implementation(libraries.ui.glide)
    kapt(libraries.ui.glideCompiler)

    testImplementation(project(":testcommons"))
    testImplementation(test.jUnit)
    testImplementation(test.archCoreTesting)
    testImplementation(test.mockk)
    testImplementation(libraries.coroutines.coroutinesTest)

    androidTestImplementation(project(":testcommons"))
    androidTestImplementation(project(":androidtestcommons"))
    androidTestImplementation(test.jUnit)
    androidTestImplementation(androidTest.mockk)
    androidTestImplementation(androidTest.runner)
    androidTestImplementation(androidTest.espressoCore)
    androidTestImplementation(androidTest.espressoContrib)
    androidTestImplementation(androidTest.espressoIntents)
    androidTestImplementation(libraries.di.daggerHilt)
    androidTestImplementation(test.daggerHilt)
    kaptAndroidTest(libraries.di.daggerHiltCompiler)
    androidTestImplementation(libraries.di.lifecycleHilt)
    kaptAndroidTest(libraries.di.lifecycleHiltCompiler)
}
