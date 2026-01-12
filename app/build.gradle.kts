plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.cefasbysoftps.addplayer"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.cefasbysoftps.addplayer"
        minSdk = 24
        targetSdk = 36
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
        compose = true
    }
}

dependencies {

    // Versión específica (verifica la última versión estable)
    val room_version = "2.6.1"

    // Versiones (verifica las más recientes)
    val hilt_version = "2.48"  // Última versión estable
    val hilt_navigation_version = "1.1.0"


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.media3.exoplayer)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("androidx.media3:media3-ui:1.4.1")
    // Navegación principal para Compose
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Si quieres integración con ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // Para argumentos de navegación (opcional)
    implementation("androidx.navigation:navigation-common:2.8.0")

    // OkHttp Core
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Logging Interceptor (MUY recomendado para debug)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Retrofit base
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Convertidor JSON (elige uno)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")



    // Room core
    implementation("androidx.room:room-runtime:$room_version")

    // Kotlin Extensions y Coroutines support
    implementation("androidx.room:room-ktx:$room_version")

    // Anotaciones processor
    kapt("androidx.room:room-compiler:$room_version")

    // Para usar corrutinas con Room
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")





}