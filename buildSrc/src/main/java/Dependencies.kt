@file:Suppress("ClassName")

// lowercase usage for dsl
object libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0"

    object androidX {
        private const val lifecycleVersion = "2.2.0"
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val core = "androidx.core:core-ktx:1.3.0"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
        const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion"
        const val viewModelScope = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    }

    object coroutines {
        private const val coroutinesVersion = "1.3.3"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
    }

    object network {
        private const val okHttpVersion = "4.7.2"
        const val okhttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$okHttpVersion"
        const val logginIntercepor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val retrofitConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.5.0"
    }

    object di {
        private const val lifecycleHiltVersion = "1.0.0-alpha01"
        const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
        const val daggerHiltCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
        const val lifecycleHilt = "androidx.hilt:hilt-lifecycle-viewmodel:$lifecycleHiltVersion"
        const val lifecycleHiltCompiler = "androidx.hilt:hilt-compiler:$lifecycleHiltVersion"
    }

    object ui {
        private const val glideVersion = "4.11.0"
        const val material = "com.google.android.material:material:1.3.0-alpha01"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta7"
        const val glide = "com.github.bumptech.glide:glide:$glideVersion"
        const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
    }
}

object test {
    const val jUnit = "junit:junit:4.12"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val archCoreTesting = "android.arch.core:core-testing:2.1.0"
    const val daggerHilt = "com.google.dagger:hilt-android-testing:${Versions.daggerHilt}"
}

object androidTest {
    const val runner = "androidx.test:runner:1.1.1"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:3.2.0"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:3.2.0"
    const val jUnit = "androidx.test.ext:junit:1.1.1"
    const val mockk = "io.mockk:mockk-android:${Versions.mockk}"
}