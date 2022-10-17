import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	java
	application
	id("org.springframework.boot") version "{gradle-project-version}"
}

// tag::main-class[]
application {
	mainClass.set("com.example.ExampleApplication")
}
// end::main-class[]
