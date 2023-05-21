// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.jvm") version Dep.kotlin
    id("org.jetbrains.kotlin.kapt") version Dep.kotlin
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}

buildscript{
    repositories {
        google()  // Google"s Maven repository
        mavenCentral()
        maven {
            url = uri("https://maven.fabric.io/public")
        }
        maven { url = uri("https://jitpack.io") }
    }

    dependencies{
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
        classpath("com.android.tools.build:gradle:7.4.2")
        // Add the Crashlytics Gradle plugin.
        //classpath("com.google.gms:google-services:${Dep.gms_google_services}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Dep.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dep.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Dep.hilt}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.fabric.io/public")
        }
    }
}

