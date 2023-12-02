plugins {
    id("common-conventions")
    id("xyz.jpenilla.gremlin-gradle")
}

val runtimeDownloadOnlyApi by configurations.registering {
    extendsFrom(configurations.compileOnlyApi.get())
    extendsFrom(configurations.runtimeDownload.get())
}

val runtimeDownloadApi by configurations.registering {
    extendsFrom(configurations.api.get())
    extendsFrom(configurations.runtimeDownload.get())
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
