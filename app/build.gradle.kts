plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.semana_04_proyecto_prise_rest_gim"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.semana_04_proyecto_prise_rest_gim"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    
    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    
    // PDF
    implementation("com.itextpdf.android:kernel-android:7.2.5")
    implementation("com.itextpdf.android:layout-android:7.2.5")
    implementation("com.itextpdf.android:io-android:7.2.5")
}