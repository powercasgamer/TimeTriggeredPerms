import java.util.*

plugins {
    id("common-conventions")
    id("kotlin-conventions")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("reflect"))
    compileOnlyApi("net.luckperms:api:5.4")
    compileOnly("org.spongepowered:configurate-hocon:4.2.0-SNAPSHOT")
    compileOnly("org.spongepowered:configurate-extra-kotlin:4.2.0-SNAPSHOT")
}

applyJarMetadata("timetriggeredperms-core")

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
