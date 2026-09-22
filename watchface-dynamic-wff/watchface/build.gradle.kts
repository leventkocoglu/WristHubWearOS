plugins {
    id("com.android.application")
}
android {
    enableKotlin = false
    namespace = "com.wristhub.dynamicface"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.wristhub.dynamicface"
        minSdk = 33
        targetSdk = 35
        versionCode = 2
        versionName = "2.0"
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
