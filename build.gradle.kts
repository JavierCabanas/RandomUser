// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.build.gradle.kts.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register(name="clean",type = Delete::class) {
    delete(rootProject.buildDir)
}