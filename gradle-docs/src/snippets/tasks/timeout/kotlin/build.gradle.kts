import java.time.Duration

// tag::without-import[]
tasks.register("hangingTask") {
    doLast {
        Thread.sleep(100000)
    }
    timeout.set(Duration.ofMillis(500))
}
// end::without-import[]
