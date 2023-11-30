import java.util.*

plugins {
    id("common-conventions")
    id("kotlin-conventions")
    id("velocity-conventions")
    kotlin("kapt")
    id("xyz.jpenilla.gremlin-gradle")
}

fun DependencyHandler.runtimeDownloadApi(dependencyNotation: Any) {
    api(dependencyNotation)
    runtimeDownload(dependencyNotation)
}

fun DependencyHandler.runtimeDownloadOnlyApi(dependencyNotation: Any) {
    compileOnlyApi(dependencyNotation)
    runtimeDownload(dependencyNotation)
}

dependencies {
    api(projects.timetriggeredpermsCore)
    compileOnly("net.luckperms:api:5.4")
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    kapt("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    implementation(libs.gremlin.runtime)
    runtimeDownloadOnlyApi(kotlin("stdlib"))
    runtimeDownloadOnlyApi(kotlin("reflect"))
    runtimeDownloadOnlyApi("org.spongepowered:configurate-yaml:4.2.0-SNAPSHOT")
    runtimeDownloadOnlyApi("org.spongepowered:configurate-extra-kotlin:4.2.0-SNAPSHOT")
    implementation("org.bstats:bstats-velocity:3.0.2")
}

applyJarMetadata("timetriggeredperms-velocity")

tasks {
    clean {
        delete("run")
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks.writeDependencies {
    outputFileName.set("dependencies.txt")
    repos.add("https://repo.papermc.io/repository/maven-public/")
    repos.add("https://repo.maven.apache.org/maven2/")
    repos.add("https://maven.mizule.dev/")
    repos.add("https://maven.reposilite.com/snapshots/")
    repos.add("https://maven.reposilite.com/releases/")
}

gremlin {
    defaultJarRelocatorDependencies.set(true)
    defaultGremlinRuntimeDependency.set(false)
}

configurations.runtimeDownload {
    exclude("org.checkerframework", "checker-qual")
    exclude("org.jetbrains", "annotations")
}
