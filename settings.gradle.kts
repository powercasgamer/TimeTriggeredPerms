pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "TimeTriggeredPerms"

sequenceOf(
    "paper",
    "velocity",
    "core"
).forEach {
    include("timetriggeredperms-$it")
    project(":timetriggeredperms-$it").projectDir = file(it)
}
