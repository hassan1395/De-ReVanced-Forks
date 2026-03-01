pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "morphe-patches"
include(":patches")
include(":extensions:shared")
include(":extensions:shared:library")
include(":extensions:cricbuzz")
include(":extensions:cricbuzz:stub")
include(":extensions:strava")
include(":extensions:strava:stub")

// Apply Android plugin to all stub and library modules
gradle.beforeProject {
    if (project.name.endsWith("stub") || project.name == "library") {
        project.apply(plugin = "com.android.library")
    }
}
