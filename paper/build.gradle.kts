import java.util.*

plugins {
    id("common-conventions")
    id("kotlin-conventions")
    id("paper-conventions")
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
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
    implementation(libs.gremlin.runtime)

    runtimeDownloadOnlyApi(kotlin("stdlib"))
    runtimeDownloadOnlyApi(kotlin("reflect"))
    runtimeDownloadOnlyApi("org.spongepowered:configurate-yaml:4.2.0-SNAPSHOT")
    runtimeDownloadOnlyApi("org.spongepowered:configurate-extra-kotlin:4.2.0-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

applyJarMetadata("timetriggeredperms-paper")

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
