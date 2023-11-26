import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("common-conventions")
//    id("io.papermc.paperweight.userdev")
    id("xyz.jpenilla.run-paper")
    id("xyz.jpenilla.gremlin-gradle")
}

tasks {
    runServer {
        minecraftVersion("1.20.2")

        jvmArguments.add("-Dcom.mojang.eula.agree=true")
        systemProperty("terminal.jline", false)
        systemProperty("terminal.ansi", true)
        args( "-p", "25519")

        downloadPlugins {
            url("https://github.com/MiniPlaceholders/MiniPlaceholders/releases/download/2.2.1/MiniPlaceholders-Paper-2.2.1.jar")
            url("https://download.luckperms.net/1519/bukkit/loader/LuckPerms-Bukkit-5.4.106.jar")
        }
    }

    named("clean", Delete::class) {
        delete(project.projectDir.resolve("run"))
    }


    shadowJar {
//        dependencies {
//            exclude(dependency(libs.adventure.api.get()))
//            exclude(dependency(libs.adventure.legacy.get()))
//            exclude(dependency(libs.adventure.key.get()))
//            exclude(dependency(libs.adventure.plain.get()))
//            include(dependency(libs.gremlin.gradle.get().toString()))
//            include(dependency(libs.gremlin.runtime.get().toString()))
//        }
        relocate("xyz.jpenilla.gremlin", "dev.mizule.timetriggeredperms.lib.xyz.jpenilla.gremlin")
    }

//    writeDependencies {
//        repos.set(listOf(
//            "https://repo.papermc.io/repository/maven-public/",
//            "https://repo.broccol.ai/releases",
//            "https://maven.mizule.dev/",
//            "https://oss.sonatype.org/content/repositories/snapshots/",
//            "https://s01.oss.sonatype.org/content/repositories/snapshots/",
//            "https://repo.jpenilla.xyz/snapshots/",
//            "https://repo.jpenilla.xyz/releases/",
//            "https://jitpack.io"
//        ))
//    }
}

//configurations.runtimeDownload {
//    exclude("io.papermc.paper")
//    exclude("net.kyori", "adventure-api")
//    exclude("net.kyori", "adventure-text-minimessage")
//    exclude("net.kyori", "adventure-text-serializer-plain")
//    exclude("org.slf4j", "slf4j-api")
//    exclude("org.ow2.asm")
//}
//
//configurations.paperweightDevelopmentBundle {
//    resolutionStrategy.cacheChangingModulesFor(3, "days")
//}