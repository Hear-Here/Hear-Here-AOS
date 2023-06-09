import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}
fun getLocalKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

android {
    namespace = "com.hearhere.data"
    compileSdk = App.targetSdk

    defaultConfig {
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "encrypted_pref", getLocalKey("encrypted_pref"))
        buildConfigField("String", "PREF_KEY", getLocalKey("pref_key"))
        buildConfigField("String", "BASE_URL", "\"${getLocalKey("BASE_URL")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            extra["enableCrashlytics"] = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = App.jvmTarget
    }
}

dependencies {
    implementation(project(path = ":domain"))

    implementation("org.jetbrains.kotlin:kotlin-serialization:${Dep.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("com.google.dagger:hilt-android:${Dep.hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Dep.hilt_androidx_compiler}")
    implementation("androidx.constraintlayout:constraintlayout:${Dep.constraintLayout}")
    implementation("androidx.appcompat:appcompat:${Dep.androidx_appCompat}")

    // pref
    implementation("androidx.datastore:datastore:${Dep.data_store}")
    implementation("androidx.datastore:datastore-preferences:${Dep.data_store}")
    implementation("androidx.datastore:datastore-rxjava2:${Dep.data_store}")

    // retrofit, okhttp
    implementation("com.squareup.retrofit2:retrofit:${Dep.retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${Dep.retrofit_converter_gson}")
    implementation("com.squareup.okhttp3:okhttp:${Dep.okhttp}")
    implementation("com.squareup.retrofit2:converter-jackson:${Dep.retrofit_converter_jackson}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Dep.logging_interceptor}")

    implementation("com.squareup.retrofit2:converter-simplexml:2.1.0")
}
