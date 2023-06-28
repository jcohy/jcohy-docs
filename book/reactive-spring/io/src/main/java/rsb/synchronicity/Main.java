package rsb.synchronicity;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:17:13
 * @since 2022.04.0
 */
@EnableAsync
@SpringBootApplication
public class Main implements AsyncConfigurer {

    // <2>

    private final Executor executor;

    Main(Executor executor) {
        this.executor = executor;
    }

    @Bean
    ApplicationRunner runner(AlgorithmClient algorithm) {
        return args -> {
            var max = 12;// <3>
            var runners = List.of(// <4>
                    new AsyncRunner(algorithm, max), new SyncRunner(algorithm, max));
            runners.forEach(Runnable::run);
        };
    }

    @Override
    public Executor getAsyncExecutor() {
        return this.executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
