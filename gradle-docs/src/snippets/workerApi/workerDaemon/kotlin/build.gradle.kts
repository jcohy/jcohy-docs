// The parameters for a single unit of work
interface ReverseParameters : WorkParameters {
    val fileToReverse : RegularFileProperty
    val destinationDir : DirectoryProperty
}

// The implementation of a single unit of work
abstract class ReverseFile @Inject constructor(val fileSystemOperations: FileSystemOperations) : WorkAction<ReverseParameters> {
    override fun execute() {
        val fileToReverse = parameters.fileToReverse.asFile.get()
        fileSystemOperations.copy {
            from(fileToReverse)
            into(parameters.destinationDir)
            filter { line: String -> line.reversed() }
        }
        if (java.lang.Boolean.getBoolean("org.gradle.sample.showFileSize")) {
            println("Reversed ${fileToReverse.length()} bytes from ${fileToReverse.name}")
        }
    }
}

open class ReverseFiles @Inject constructor(private val workerExecutor: WorkerExecutor) : SourceTask() {
    @OutputDirectory
    lateinit var outputDir: File

    @TaskAction
    fun reverseFiles() {
        // tag::worker-daemon[]
        // Create a WorkQueue with process isolation
        val workQueue = workerExecutor.processIsolation() {
            // Configure the options for the forked process
            forkOptions {
                maxHeapSize = "512m"
                systemProperty("org.gradle.sample.showFileSize", "true")
            }
        }

        // Create and submit a unit of work for each file
        source.forEach { file ->
            workQueue.submit(ReverseFile::class) {
                fileToReverse.set(file)
                destinationDir.set(outputDir)
            }
        }
        // end::worker-daemon[]
    }
}

tasks.register<ReverseFiles>("reverseFiles") {
    outputDir = file("$buildDir/reversed")
    source("sources")
}
