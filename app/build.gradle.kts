plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.alunos.odontofast"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.alunos.odontofast"
        minSdk = 33
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
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.retrofit) // Retrofit para chamadas de API
    implementation(libs.converter.gson) // Converter Gson para Retrofit
    implementation(libs.androidx.core.ktx) // Extensões do Core KTX
    implementation(libs.androidx.appcompat) // Biblioteca de compatibilidade
    implementation(libs.material) // Material Design
    implementation(libs.androidx.activity) // Activity KTX
    implementation(libs.androidx.constraintlayout) // Layout de Constraint
    implementation(libs.gson) // Biblioteca Gson
    implementation("com.oracle.database.jdbc:ojdbc8:19.8.0.0") // Driver JDBC da Oracle
    implementation(libs.recyclerview) // RecyclerView para listas
    implementation("androidx.work:work-runtime-ktx:2.7.1") // Biblioteca de WorkManager
    testImplementation(libs.junit) // JUnit para testes
    androidTestImplementation(libs.androidx.junit) // JUnit para testes Android
    androidTestImplementation(libs.androidx.espresso.core) // Espresso para testes de UI
}
