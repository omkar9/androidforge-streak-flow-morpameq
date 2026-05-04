plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false // Hilt Gradle Plugin
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // These are plugins for the build system, not direct dependencies for the app
        classpath("com.android.tools.build:gradle:8.2.0") // Android Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0") // Kotlin Gradle Plugin
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48") // Hilt Gradle Plugin
    }
}