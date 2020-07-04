plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(project(":core"))
    implementation(libraries.kotlin)
    implementation(libraries.coroutines.core)
    implementation(libraries.di.daggerHilt)
    kapt(libraries.di.daggerHiltCompiler)

    testImplementation(project(":testcommons"))
    testImplementation(test.jUnit)
    testImplementation(test.mockk)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
