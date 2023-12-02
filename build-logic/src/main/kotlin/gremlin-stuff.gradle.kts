import org.jetbrains.kotlin.gradle.utils.extendsFrom

plugins {
    id("common-conventions")
    id("xyz.jpenilla.gremlin-gradle")
}

val runtimeDownloadOnlyApi by configurations.registering
val runtimeDownloadApi by configurations.registering

configurations {
    compileOnlyApi.extendsFrom(runtimeDownloadOnlyApi)
    runtimeDownload.extendsFrom(runtimeDownloadOnlyApi)
    api.extendsFrom(runtimeDownloadApi)
    runtimeDownload.extendsFrom(runtimeDownloadApi)
}

dependencies {
    implementation(libs.gremlin.runtime)
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
