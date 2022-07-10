import io.cryptopunks.gradle.client.dependency.Dependency
import io.cryptopunks.gradle.client.dependency.addCustomRepositories

plugins {
    `kotlin-dsl`

    id("io.cryptopunks.gradle.client.dependency")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    addCustomRepositories()
}

dependencies {
    implementation(Dependency.gradle.dependency)
    implementation(Dependency.gradle.serialization)
    implementation(Dependency.gradle.coverage)
    implementation(Dependency.gradle.spotless)
    implementation(Dependency.gradle.projectConfig)
    implementation(Dependency.gradle.runtimeConfig)
    implementation(Dependency.gradle.sqldelight)
    implementation(Dependency.gradle.hilt)
    implementation(Dependency.gradle.kmock)
    implementation(Dependency.gradle.rustAndroid)
}
