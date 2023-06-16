@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

repositories {
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.1")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.1")
    implementation("io.ktor:ktor-server-status-pages-jvm:2.3.1")
    implementation("io.ktor:ktor-server-default-headers-jvm:2.3.1")
}
