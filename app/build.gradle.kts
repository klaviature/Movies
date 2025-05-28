plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.movies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movies"
        minSdk = 24
        targetSdk = 34
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
            isDebuggable = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    // =================================== Compose ================================================
    val composeBom = platform("androidx.compose:compose-bom:2025.05.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // Optional - Add full set of material icons
    // implementation("androidx.compose.material:material-icons-extended")

    // Optional - Integration with activities
    // implementation("androidx.activity:activity-compose:1.10.1")
    // Optional - Integration with ViewModels
    // implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    // Optional - Integration with LiveData
    // implementation("androidx.compose.runtime:runtime-livedata")
    // Optional - Integration with RxJava
    // implementation("androidx.compose.runtime:runtime-rxjava2")

    // ===================================== Other ================================================
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)

    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.adapterRxjava3)

    implementation(libs.room.runtime)
    implementation(libs.room.rxjava3)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    ksp(libs.room.compiler)

    implementation(libs.glide)

    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}