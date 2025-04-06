// Root-level build.gradle.kts

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add the classpath for Google Services plugin
        classpath(libs.google.services)
    }
}

allprojects {
    repositories {
        // Don't add repositories here to avoid the conflict
    }
}
