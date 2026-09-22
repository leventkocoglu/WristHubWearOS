plugins {
    id("com.android.application")
}
android {
    namespace = "com.wristhub.classic"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.wristhub.classic"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        debug { isMinifyEnabled = false }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}
