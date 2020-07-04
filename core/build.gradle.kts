plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libraries.kotlin)
    implementation(libraries.coroutines.core)

    testImplementation(test.jUnit)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
