package rsb.io.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:43
 * @since 2022.04.0
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    Executor executor() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    ApplicationRunner runner(Map<String, FilesystemFileSync> filesystemFileSyncMap, Executor executor) throws IOException {
        var file = Files//
                .createTempFile("io-content-data", ".txt")//
                .toFile();
        file.deleteOnExit();
        try (var in = Main.class.getResourceAsStream("/content"); var out = new FileOutputStream(file)) {
            FileCopyUtils.copy(in, out);
        }
        log.info("file.length: " + file.length());

        return args -> filesystemFileSyncMap.forEach((beanName, fss) -> {
            var classSimpleName = fss.getClass().getSimpleName().toLowerCase(Locale.ROOT);
            log.info("running " + classSimpleName);
            // <2>
            executor.execute(() -> {
                try {
                    fss.start(file, new BytesConsumer(file, beanName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    static class BytesConsumer implements Consumer<byte[]> {

        private static final Logger log = LoggerFactory.getLogger(BytesConsumer.class);

        private final File source;

        private final String prefix;

        public BytesConsumer(File source, String prefix) {
            this.source = source;
            this.prefix = prefix;
        }

        @Override
        public void accept(byte[] bytes) {
            log.info(prefix + ':' + bytes.length + ':' + source.getAbsolutePath());
        }

    }
}
