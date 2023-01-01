plugins {
    id("java-platform")
}

group = "com.example.platform"

dependencies {
    constraints {
        api("com.android.tools.build:gradle:4.1.1")
        api("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:1.4.20")
        api("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.4.20")
        api("org.springframework.boot:org.springframework.boot.gradle.plugin:2.4.0")
    }
}
