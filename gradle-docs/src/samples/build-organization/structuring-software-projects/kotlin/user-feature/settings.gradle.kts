// == Define locations for build logic ==
pluginManagement {
    repositories {
        gradlePluginPortal() // if pluginManagement.repositories looks like this, it can be omitted as this is the default
    }
}
includeBuild("../platforms")
includeBuild("../build-logic")

// == Define locations for components ==
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
includeBuild("../domain-model")
includeBuild("../state")

// == Define the inner structure of this component ==
rootProject.name = "user-feature"
include("table")
include("data")
