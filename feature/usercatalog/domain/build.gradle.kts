plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))
    implementation(libraries.kotlin)
    implementation(libraries.coroutines.core)

    testImplementation(project(":testcommons"))
    testImplementation(test.jUnit)
    testImplementation(test.mockk)
}
