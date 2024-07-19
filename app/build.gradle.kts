plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.arkteya.vkneewsclient"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.arkteya.vkneewsclient"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true


        }
    }

    // Добавление значений в Manifest Placeholders.
    android {

        defaultConfig {
            addManifestPlaceholders(
                mapOf(
                    "VKIDClientID" to "52011723", // ID вашего приложения (app_id).
                    "VKIDClientSecret" to "1SoDTnPBROSPcZCaU7SG", // Ваш защищенный ключ (client_secret).
                    "VKIDRedirectHost" to "vk.com", // Обычно используется vk.com.
                    "VKIDRedirectScheme" to "vk52011723", // Обычно используется vk{ID приложения}.
                )
            )
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}



dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.google.code.gson:gson:2.10")

    implementation("io.coil-kt:coil-compose:2.1.0")

    implementation("com.vk:android-sdk-core:4.1.0")
    implementation("com.vk:android-sdk-api:4.1.0")

    implementation("androidx.compose.material:material:1.4.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")
    implementation("androidx.navigation:navigation-compose:2.7.7")       //навигация по экранам с сохранением состояния действий экрана и эранов в целом
}