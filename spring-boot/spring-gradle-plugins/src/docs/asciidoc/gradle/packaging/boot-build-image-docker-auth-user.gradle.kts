import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	java
	id("org.springframework.boot") version "{gradle-project-version}"
}

tasks.named<BootJar>("bootJar") {
	mainClass.set("com.example.ExampleApplication")
}

// tag::docker-auth-user[]
tasks.named<BootBuildImage>("bootBuildImage") {
	docker {
		builderRegistry {
			username = "user"
			password = "secret"
			url = "https://docker.example.com/v1/"
			email = "user@example.com"
		}
	}
}
// end::docker-auth-user[]

tasks.register("bootBuildImageDocker") {
	doFirst {
		println("username=${tasks.getByName<BootBuildImage>("bootBuildImage").docker.builderRegistry.username}")
		println("password=${tasks.getByName<BootBuildImage>("bootBuildImage").docker.builderRegistry.password}")
		println("url=${tasks.getByName<BootBuildImage>("bootBuildImage").docker.builderRegistry.url}")
		println("email=${tasks.getByName<BootBuildImage>("bootBuildImage").docker.builderRegistry.email}")
	}
}
