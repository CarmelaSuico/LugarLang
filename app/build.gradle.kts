plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
<<<<<<< HEAD
    namespace = "usc.edu.lugarlang_login"
    compileSdk = 36

    defaultConfig {
        applicationId = "usc.edu.lugarlang_login"
        minSdk = 24
        targetSdk = 36
=======
    namespace = "usc.edu.lugarlang"
    compileSdk = 35

    defaultConfig {
        applicationId = "usc.edu.lugarlang"
        minSdk = 24
        targetSdk = 35
>>>>>>> 8ea93ce348105dc75d02c2170edf79010d66dd50
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
<<<<<<< HEAD

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.core)
    implementation(libs.lifecycle)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
=======
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.androidx.recyclerview)
    implementation(libs.osmdroid.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
>>>>>>> 8ea93ce348105dc75d02c2170edf79010d66dd50
}