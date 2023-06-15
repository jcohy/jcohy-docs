package rsb.io.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:17:10
 * @since 2022.04.0
 */
@SpringBootApplication
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // <1>
    @Bean
    ApplicationRunner runner(Map<String, NetworkFileSync> networkFileSyncMap, Executor executor) {
        var ctr = new AtomicInteger();

        return args -> networkFileSyncMap.forEach((beanName, nfs) -> {
            var port = ctr.getAndIncrement() + 8008;// <1>
            var classSimpleName = nfs.getClass().getSimpleName().toLowerCase(Locale.ROOT);
            log.info("running " + classSimpleName + " on port " + port);
            // <2>
            executor.execute(() -> {
                try {
                    nfs.start(port, // <3>
                            bytes -> log.info(beanName + " read " + bytes.length + " bytes"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

}
