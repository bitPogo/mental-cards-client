pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

includeBuild("gradlePlugin/mental-cards-dependency")

include(
    ":example-android-application",
    ":util",
    ":bigint",
    ":crypto",
    ":deck"
)

buildCache {
    local {
        isEnabled = false
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

rootProject.name = "mental-client"
