plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.hearhere.presentation"
    compileSdk = App.targetSdk

    buildFeatures{
        dataBinding = true
    }

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
    implementation("androidx.appcompat:appcompat:${Dep.androidx_appCompat}")


    // androidx
    implementation("androidx.core:core-ktx:${Dep.coreKtx}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Dep.lifecycle_viewmodel_ktx}")
    implementation("androidx.lifecycle:lifecycle-extensions:${Dep.lifecycleExt}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Dep.lifecycle_runtime_ktx}")
    implementation("androidx.work:work-runtime-ktx:${Dep.work_runtime_ktx}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${Dep.lifecycle_common_java8}")
    implementation("androidx.activity:activity-ktx:${Dep.activity_ktx}")
    implementation("androidx.fragment:fragment-ktx:${Dep.fragment_ktx}")
    implementation("androidx.annotation:annotation:${Dep.androidx_annotation}")
    implementation(("com.google.android.material:material:${Dep.material}"))

    implementation("com.google.dagger:hilt-android:${Dep.hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Dep.hilt_androidx_compiler}")

    implementation("androidx.constraintlayout:constraintlayout:${Dep.constraintLayout}")


}