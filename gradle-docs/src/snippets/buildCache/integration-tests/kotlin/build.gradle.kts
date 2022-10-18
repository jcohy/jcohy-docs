plugins {
    id("java-library")
}

val taskProvider = tasks.register<Test>("integTest")
val TaskContainer.integTest: TaskProvider<Test> get() = taskProvider // define accessor we would get if 'integTest' was defined in plugin

// tag::integTest[]
tasks.integTest {
    inputs.property("operatingSystem") {
        System.getProperty("os.name")
    }
}
// end::integTest[]

// tag::distributionPathInput[]
// Don't do this! Breaks relocatability!
tasks.integTest {
    systemProperty("distribution.location", layout.buildDirectory.dir("dist").get().asFile.absolutePath)
}
// end::distributionPathInput[]

// tag::distributionDirInput[]
class DistributionLocationProvider(                                     // <1>
    @InputDirectory
    @PathSensitive(PathSensitivity.RELATIVE)                            // <2>
    var distribution: File
) : CommandLineArgumentProvider {

    override fun asArguments(): Iterable<String> =
        listOf("-Ddistribution.location=${distribution.absolutePath}")  // <3>
}

tasks.integTest {
    jvmArgumentProviders.add(
        DistributionLocationProvider(layout.buildDirectory.dir("dist").get().asFile) // <4>
    )
}
// end::distributionDirInput[]

// tag::ignoreSystemProperties[]
class CiEnvironmentProvider : CommandLineArgumentProvider {
    @Internal                                                // <1>
    val agentNumber = System.getenv()["AGENT_NUMBER"] ?: "1"

    override fun asArguments(): Iterable<String> =
        listOf("-DagentNumber=$agentNumber")                 // <2>
}

tasks.integTest {
    jvmArgumentProviders.add(
        CiEnvironmentProvider()                              // <3>
    )
}
// end::ignoreSystemProperties[]

// tag::environment[]
tasks.integTest {
    inputs.property("langEnvironment") {
        System.getenv("LANG")
    }
}
// end::environment[]
