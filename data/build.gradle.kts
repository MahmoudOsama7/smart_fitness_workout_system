plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.data"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        buildConfig = true
    }

    flavorDimensions += "environment"

    productFlavors {
        create("Production") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "${properties["PRODUCTION_BASE_URL"]}")
            buildConfigField("Boolean", "IS_OFFLINE_MODE", "false")
        }
        create("Demo") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "${properties["DEMO_BASE_URL"]}")
            buildConfigField("Boolean", "IS_OFFLINE_MODE", "true")
        }
    }

}

dependencies {
    implementation(projects.domain)
    implementation(projects.database)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    kapt(libs.hilt.android.compiler)
    implementation(libs.bundles.hilt)
    implementation(libs.gson)
    implementation(libs.retrofit.converter.gson)
}