pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DocViewer"

include(":app")
include(":core:model", ":core:common", ":core:designsystem")
include(":domain")
include(":data")
include(":engine:api", ":engine:pdf", ":engine:docx", ":engine:xlsx", ":engine:pptx", ":engine:text")
include(":feature:home", ":feature:files", ":feature:viewer", ":feature:settings")
