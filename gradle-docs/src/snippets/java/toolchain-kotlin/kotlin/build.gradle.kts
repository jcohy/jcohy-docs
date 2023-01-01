import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    kotlin("jvm") version "1.4.20"
}

repositories {
    mavenCentral()
}

// tag::compiler-kotlin[]
val compiler = javaToolchains.compilerFor {
    languageVersion.set(JavaLanguageVersion.of(11))
}

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions.jdkHome = compiler.get().metadata.installationPath.asFile.absolutePath
}
// end::compiler-kotlin[]
