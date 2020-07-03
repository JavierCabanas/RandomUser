@file:Suppress("ClassName")

// lowercase usage for dsl
object libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

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
}

object test {
    const val jUnit = "junit:junit:4.12"
}

object androidTest {
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    const val jUnit = "androidx.test.ext:junit:1.1.1"
}