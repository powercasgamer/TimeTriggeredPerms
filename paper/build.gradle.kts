plugins {
    id("common-conventions")
    id("kotlin-conventions")
    id("paper-conventions")
    id("gremlin-stuff")
}

dependencies {
    api(projects.timetriggeredpermsCore)
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
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
