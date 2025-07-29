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
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "platzi-movies"
include(":app")
include(":core:ui")
project(":core:ui").projectDir = file("core/ui")
include(":core:network")
project(":core:network").projectDir = file("core/network")
include(":core:database")
project(":core:database").projectDir = file("core/database")
include(":core:media")
project(":core:media").projectDir = file("core/media")
include(":core:common")
project(":core:common").projectDir = file("core/common")
include(":core:di")
project(":core:di").projectDir = file("core/di")
include(":feature:movies:data")
project(":feature:movies:data").projectDir = file("feature/movies/data")
include(":feature:movies:domain")
project(":feature:movies:domain").projectDir = file("feature/movies/domain")
include(":feature:movies:ui")
project(":feature:movies:ui").projectDir = file("feature/movies/ui")