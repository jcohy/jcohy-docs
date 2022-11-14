plugins { id("base") }

repositories {
    mavenCentral()
}

val codec = configurations.create("codec") {
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
    }
    isVisible = false
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    codec("commons-codec:commons-codec:1.10")
}

tasks.register<CreateMD5>("md5") {
    codecClasspath.from(codec)
    destinationDirectory.set(project.layout.buildDirectory.dir("md5"))
    source(project.layout.projectDirectory.file("src"))
}
