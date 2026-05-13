import java.util.Date
import java.text.SimpleDateFormat

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.challengembco"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.challengembco"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            if (project.hasProperty("STORE_FILE")) {
                storeFile = file(project.property("STORE_FILE") as String)
                storePassword = project.property("STORE_PASSWORD") as String
                keyAlias = project.property("KEY_ALIAS") as String
                keyPassword = project.property("KEY_PASSWORD") as String
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
    // implementation("com.squareup.okhttp3:okhttp:3.4.1") // Prueba dependabot
    // implementation("commons-collections:commons-collections:3.2.1")  // Prueba dependabot + dependency review + TEST
    // implementation("org.apache.commons:commons-text:1.9") // 
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// configurations.configureEach {
//     resolutionStrategy.activateDependencyLocking()
// }

configurations.all {
    resolutionStrategy {

        force("org.jdom:jdom2:2.0.6.1")

        force("org.bouncycastle:bcprov-jdk18on:1.80")

        force("org.bitbucket.b_c:jose4j:0.9.6")

        force("io.netty:netty-handler:4.1.118.Final")
        force("io.netty:netty-codec-http2:4.1.118.Final")
        force("io.netty:netty-codec-http:4.1.118.Final")
        force("io.netty:netty-codec:4.1.118.Final")

        force("com.google.protobuf:protobuf-java:3.25.5")
        force("com.google.protobuf:protobuf-kotlin:3.25.5")
    }
}

configurations.configureEach {
    resolutionStrategy.activateDependencyLocking()
}

// ── Remote Config Generator ──────────────────────────────────────────────────

data class RemoteConfigFlag(
    val key: String,
    val defaultValue: Any,
    val valueType: String,
    val description: String
)

val bankFlags = listOf(
    RemoteConfigFlag("transferencias_v2_enabled", false,   "BOOLEAN", "Activa flujo de transferencias v2"),
    RemoteConfigFlag("biometria_enabled",         true,    "BOOLEAN", "Habilita autenticacion biometrica"),
    RemoteConfigFlag("modo_mantenimiento",        false,   "BOOLEAN", "Muestra pantalla de mantenimiento"),
    RemoteConfigFlag("min_app_version",           "2.0.0", "STRING",  "Version minima requerida"),
    RemoteConfigFlag("max_monto_transferencia",   50000L,  "NUMBER",  "Limite maximo de transferencia"),
    RemoteConfigFlag("limite_retiro_atm",         10000L,  "NUMBER",  "Limite diario retiro ATM")
)

tasks.register("generateRemoteConfig") {
    group = "firebase"
    description = "Genera remote-config/config.json desde bankFlags"

    doLast {
        val duplicates = bankFlags.groupBy { it.key }.filter { it.value.size > 1 }.keys
        if (duplicates.isNotEmpty()) {
            throw GradleException("Keys duplicadas: $duplicates")
        }

        val today = SimpleDateFormat("yyyy-MM-dd").format(Date())

        val params = bankFlags.joinToString(",\n") { flag ->
            "    \"${flag.key}\": {\n" +
                    "      \"defaultValue\": { \"value\": \"${flag.defaultValue}\" },\n" +
                    "      \"description\": \"${flag.description}\",\n" +
                    "      \"valueType\": \"${flag.valueType}\"\n" +
                    "    }"
        }

        val json = "{\n" +
                "  \"parameters\": {\n" +
                "$params\n" +
                "  },\n" +
                "  \"version\": {\n" +
                "    \"description\": \"Autogenerado el $today - NO editar manualmente\",\n" +
                "    \"flagCount\": ${bankFlags.size}\n" +
                "  }\n" +
                "}"

        val outputFile = rootProject.file("remote-config/config.json")
        outputFile.parentFile.mkdirs()
        outputFile.writeText(json)

        println("Generado con ${bankFlags.size} flags:")
        bankFlags.forEach { println("  - ${it.key} (${it.valueType}) = ${it.defaultValue}") }
    }
}

tasks.register("printRuntimeDeps") {
    doLast {
        configurations.getByName("releaseRuntimeClasspath")
            .resolvedConfiguration
            .firstLevelModuleDependencies
            .forEach {
                println("${it.moduleGroup}:${it.moduleName}:${it.moduleVersion}")
            }
    }
}