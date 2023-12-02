import java.util.*

plugins {
    id("common-conventions")
    id("kotlin-conventions")
    id("velocity-conventions")
    kotlin("kapt")
    id("gremlin-stuff")
}

dependencies {
    api(projects.timetriggeredpermsCore)
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    kapt("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
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
