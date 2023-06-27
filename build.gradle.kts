// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.jvm") version Dep.kotlin
    id("org.jetbrains.kotlin.kapt") version Dep.kotlin
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

buildscript {
    repositories {
        gradlePluginPortal()
        google() // Google"s Maven repository
        mavenCentral()
        maven {
            url = uri("https://maven.fabric.io/public")
        }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }

    dependencies {
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
        classpath("com.android.tools.build:gradle:7.4.2")
        // Add the Crashlytics Gradle plugin.
        // classpath("com.google.gms:google-services:${Dep.gms_google_services}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Dep.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dep.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Dep.hilt}")
        classpath("${Dep.google_map_plugin}:${Dep.google_map_gradle}")
    }
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.fabric.io/public")
        }
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
    ktlint {
        android.set(true)
        disabledRules.set(setOf("import-ordering", "package-name", "no-wildcard-imports"))
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
    }
}
