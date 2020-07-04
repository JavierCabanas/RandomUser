buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version(Versions.detekt)
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register(name = "clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Runs over whole code base without the starting overhead for each module."
    buildUponDefaultConfig = true
    autoCorrect = true
    parallel = true
    setSource(files(projectDir))
    config.setFrom(files("$rootDir/detekt.yml"))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/build/**")
    exclude("**/buildSrc/**")
    exclude("**/test/**/*.kt")
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}
