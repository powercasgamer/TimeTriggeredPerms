import com.diffplug.gradle.spotless.FormatExtension

plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    compileOnly(files(libs::class.java.protectionDomain.codeSource.location))
    implementation(libs.indra.common)
    implementation(libs.indra.git)
    implementation(libs.indra.spotless)
    implementation(libs.shadow)
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlin.std)
    implementation(libs.idea.gradle)
    implementation(libs.gremlin.gradle)
    implementation(libs.run.task)
    implementation(libs.blossom)
    implementation(libs.hangar.publish)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    target {
        compilations.configureEach {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
}

spotless {
    fun FormatExtension.applyCommon() {
        trimTrailingWhitespace()
        endWithNewline()
        encoding("UTF-8")
        toggleOffOn()
        target("*.gradle.kts")
    }
    kotlinGradle {
        applyCommon()
        ktlint("1.0.1")
    }
    kotlin {
        applyCommon()
        ktlint("1.0.1")
    }
}
