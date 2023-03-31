import org.gradle.api.JavaVersion

object App {
    const val compileSdk = 33
    const val minSdk = 24
    const val targetSdk = 33

    val javaCompileOption = JavaVersion.VERSION_11
    const val jvmTarget = "11"
}