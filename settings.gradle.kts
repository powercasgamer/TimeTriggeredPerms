pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "TimeTriggeredPerms"

sequenceOf(
    "paper",
    "core"
).forEach {
    include("timetriggeredperms-$it")
    project(":timetriggeredperms-$it").projectDir = file(it)
}
