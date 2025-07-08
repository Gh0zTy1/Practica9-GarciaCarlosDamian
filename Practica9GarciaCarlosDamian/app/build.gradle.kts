plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "garcia.carlosdamian.practica9_garciacarlosdamian"
    compileSdk = 35

    defaultConfig {
        applicationId = "garcia.carlosdamian.practica9_garciacarlosdamian"
        minSdk = 21
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics")

    // Add the dependencies for any other Firebase products you want to use
    //Firebase Firestore
    implementation("com.google.firebase:firebase-firestore-ktx")
    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    //CardView
    implementation("androidx.cardview:cardview:1.0.0")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}