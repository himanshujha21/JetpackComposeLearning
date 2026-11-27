plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    // Hilt (dependency injection) + KSP, the tool that generates Hilt's code at build time
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.productexplorerapp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.productexplorerapp"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Icons like Search, ShoppingCart and ArrowBack
    implementation(libs.androidx.compose.material.icons.core)

    // collectAsStateWithLifecycle() for reading StateFlow in Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Navigation Compose: moving between screens
    implementation(libs.androidx.navigation.compose)

    // Hilt: creates our classes (ViewModels, repositories, Retrofit) and hands them where needed
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // hiltViewModel() inside Composables
    implementation(libs.androidx.hilt.lifecycle.viewmodel.compose)

    // Networking: Retrofit, with kotlinx.serialization to read the JSON
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    // Coil: loads product images from a URL
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
