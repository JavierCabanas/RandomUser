plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))
    implementation(libraries.kotlin)
//    implementation(libraries.coroutines.core)
    implementation(libraries.coroutines.coroutinesTest)
    implementation(test.jUnit)

    testImplementation(test.jUnit)
    testImplementation(test.mockk)
}
