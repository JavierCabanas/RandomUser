plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))
    implementation(libraries.kotlin)
    implementation(libraries.coroutines.coroutinesTest)
    implementation(test.jUnit)

    testImplementation(test.jUnit)
    testImplementation(test.mockk)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
