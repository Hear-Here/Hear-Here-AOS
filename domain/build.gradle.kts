plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.hearhere.domain"
    compileSdk = App.targetSdk

    defaultConfig {
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Dep.kotlin}")
    implementation("com.google.dagger:hilt-android:${Dep.hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Dep.hilt_androidx_compiler}")
    implementation("androidx.constraintlayout:constraintlayout:${Dep.constraintLayout}")
    implementation("androidx.appcompat:appcompat:${Dep.androidx_appCompat}")

}