import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    //id("com.google.gms.google-services")
}



fun getLocalKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

android {
    namespace = "com.hearhere"
    compileSdk = App.targetSdk

    buildFeatures{
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.hearhere"
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        addManifestPlaceholders(mapOf("KAKAO_APP_KEY" to getLocalKey("KAKAO_APP_KEY")))
        buildConfigField("String", "KAKAO_APP_KEY",   "\"${getLocalKey("KAKAO_APP_KEY")}\"" )
        buildConfigField("String", "BASE_URL",   "\"${getLocalKey("BASE_URL")}\"" )
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
    implementation(project(path = ":presentation"))
    implementation(project(path = ":data"))
    implementation (project(path = ":domain"))


    implementation("com.google.dagger:hilt-android:${Dep.hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Dep.hilt_androidx_compiler}")
    implementation("androidx.appcompat:appcompat:${Dep.appCompat}")
    implementation( "androidx.security:security-crypto:${Dep.security}")
    implementation("androidx.security:security-crypto-ktx:${Dep.security}")
    implementation("androidx.datastore:datastore:${Dep.data_store}")
    implementation("androidx.datastore:datastore-preferences:${Dep.data_store}")
    implementation("androidx.datastore:datastore-rxjava2:${Dep.data_store}")

    implementation ("com.kakao.sdk:v2-all:${Dep.kakao}") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation ("com.kakao.sdk:v2-user:${Dep.kakao}") // 카카오 로그인

    androidTestImplementation("androidx.test.ext:junit:${Dep.androidxJunitExt}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Dep.espresso}")
}