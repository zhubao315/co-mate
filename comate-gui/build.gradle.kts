import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("org.jetbrains.compose")
}

group = "org.archguard.comate"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("androidx.compose.material3:material3:1.1.0-alpha06")

    // Image loading
    // implementation("io.coil-kt:coil-compose:2.3.0")

    // Dependency Injection
    implementation("org.kodein.di:kodein-di:7.20.1")
    runtimeOnly("org.kodein.di:kodein-di-framework-compose:7.20.1")

    // UI/UX Utils
    val richtextVersion = "0.16.0"
    implementation("com.halilibo.compose-richtext:richtext-commonmark:${richtextVersion}")
    implementation("com.halilibo.compose-richtext:richtext-ui-material:${richtextVersion}")
    implementation("com.halilibo.compose-richtext:richtext-ui-material3:${richtextVersion}")

    val voyagerVersion = "1.0.0-rc05"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-kodein:$voyagerVersion")
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "comate-gui"
            packageVersion = "1.0.0"
        }
    }
}


kapt {
    correctErrorTypes = true
}
