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
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion"
    }

    object coroutines {
        private const val coroutinesVersion = "1.3.3"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
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
}

object test {
    const val jUnit = "junit:junit:4.12"
    const val mockk = "io.mockk:mockk:1.10.0"
}

object androidTest {
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    const val jUnit = "androidx.test.ext:junit:1.1.1"
}