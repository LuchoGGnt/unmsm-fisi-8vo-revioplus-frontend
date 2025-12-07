plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // ADDED: plugin de Hilt para inyección de dependencias
    alias(libs.plugins.hilt.android)

    // ADDED: plugin kapt para procesadores de anotaciones (Hilt, Room)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.revioplus.app"

    // CHANGE: usar una versión estable del SDK.
    // compileSdk 36 es "preview". Para un proyecto de universidad te conviene algo estable.
    compileSdk = 36

    defaultConfig {
        applicationId = "com.revioplus.app"

        // CHANGE (opcional): tu minSdk actual es 26.
        // Para ReVio+ te sugerí 24. Puedes dejar 26 si no necesitas más devices.
        minSdk = 26  // ANTES: 26

        // CHANGE: igual que compile, apuntemos a una versión estable.
        targetSdk = 36 // ANTES: 36

        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                // Usa las reglas por defecto + tu archivo
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // Si más adelante quieres configs especiales de debug, van aquí
        }
    }

    compileOptions {
        // ADDED/CHANGE: asegúrate de usar Java 17 (recomendado con AGP recientes)
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        // ADDED/CHANGE: Kotlin compilando para JVM 17
        jvmTarget = "17"
    }

    buildFeatures {
        // Ya estás usando Compose, esto debe estar activado
        compose = true
    }

    // IMPORTANT:
    // Con el plugin nuevo "org.jetbrains.kotlin.plugin.compose" NO necesitas
    // especificar kotlinCompilerExtensionVersion. Si en tu archivo tienes:
    //
    // composeOptions {
    //     kotlinCompilerExtensionVersion = "x.y.z"
    // }
    //
    // puedes ELIMINARLO para evitar conflictos de versión.
    // Si no lo tienes, ignora este comentario.

    packaging {
        resources {
            // EXCLUDE por defecto para evitar conflictos con licencias
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    // --- Core + lifecycle + Compose base (YA LO TENÍAS, LO MANTENEMOS) ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM (controla versiones de UI, Material, etc.)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // NUEVO: para que Theme.Material3.DayNight.NoActionBar exista
    implementation(libs.google.material)

    // ADDED: Navigation Compose (para Inicio, Depositar, Perfil)
    implementation(libs.androidx.navigation.compose)

    // ADDED: Coroutines para trabajo en background, repositorios, use cases
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // ADDED: Hilt (injection de dependencias)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ADDED: Hilt + Navigation Compose (para inyectar ViewModels en composables)
    implementation(libs.androidx.hilt.navigation.compose)

    // ADDED: Room (base de datos local para usuario, depósitos, etc.)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // ADDED: Networking (Retrofit, OkHttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // --- Tests (YA LOS TENÍAS) ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}