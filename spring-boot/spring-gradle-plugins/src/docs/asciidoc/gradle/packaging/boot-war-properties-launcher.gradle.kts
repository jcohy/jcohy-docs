import org.springframework.boot.gradle.tasks.bundling.BootWar

plugins {
	war
	id("org.springframework.boot") version "{gradle-project-version}"
}

tasks.getByName<BootWar>("bootWar") {
	mainClass.set("com.example.ExampleApplication")
}

// tag::properties-launcher[]
tasks.getByName<BootWar>("bootWar") {
	manifest {
		attributes("Main-Class" to "org.springframework.boot.loader.PropertiesLauncher")
	}
}
// end::properties-launcher[]
