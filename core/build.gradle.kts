plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libraries.kotlin)
    implementation(libraries.coroutines.core)

    testImplementation(test.jUnit)
}