// tag::buildscript_block[]
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:0.4.1")
    }
}

apply(plugin = "com.jfrog.bintray")
// end::buildscript_block[]
