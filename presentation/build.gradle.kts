plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.hearhere.presentation"
    compileSdk = App.targetSdk

    buildFeatures {
        dataBinding = true
        viewBinding = true
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
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(path = ":domain"))
    api(project(path = ":presentation:common"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Dep.kotlin}")
    implementation("androidx.appcompat:appcompat:${Dep.androidx_appCompat}")
    implementation("com.google.android.flexbox:flexbox:${Dep.flexbox}")

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
    implementation("com.google.android.material:material:${Dep.material}")
    implementation("androidx.security:security-crypto:${Dep.security}")

    implementation("com.google.dagger:hilt-android:${Dep.hilt}")
    implementation("com.google.android.gms:play-services-fido:20.0.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")
    kapt("com.google.dagger:hilt-android-compiler:${Dep.hilt_androidx_compiler}")

    implementation("com.google.android.gms:play-services-maps:${Dep.google_map}")
    implementation("com.google.android.gms:play-services-location:${Dep.google_location}")

    implementation("com.github.bumptech.glide:glide:${Dep.glide}")

    implementation("androidx.constraintlayout:constraintlayout:${Dep.constraintLayout}")
    implementation("com.airbnb.android:lottie:${Dep.lottie}")

    implementation("com.kakao.sdk:v2-all:2.13.0") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.13.0") // 카카오 로그인
    implementation("com.github.bumptech.glide:glide:${Dep.glide}")
}
