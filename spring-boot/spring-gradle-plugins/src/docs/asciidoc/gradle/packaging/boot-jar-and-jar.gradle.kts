import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	java
	id("org.springframework.boot") version "{gradle-project-version}"
}

// tag::enable-jar[]
tasks.getByName<Jar>("jar") {
	enabled = true
}
// end::enable-jar[]

// tag::classifier[]
tasks.getByName<BootJar>("bootJar") {
	classifier = "boot"
}
// end::classifier[]

tasks.getByName<BootJar>("bootJar") {
	mainClass.set("com.example.Application")
}
