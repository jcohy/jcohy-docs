package rsb.javareloaded.closeable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/24:16:19
 * @since 2022.04.0
 */
public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    // <1>
    static String CONTENTS = String.format("""
			<html>
			<body><h1> Hello, world, @ %s !</h1> </body>
			</html>
			""", Instant.now().toString()).trim();

    // <2>
    static File setup() {

        try {
            Path path = Files.createTempFile("rsb", ".txt");
            var file = path.toFile();
            file.deleteOnExit();
            Files.writeString(path, CONTENTS);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // <3>
    static void error(Throwable throwable) {// <2>
        log.error("there's been an exception processing the read! ", throwable);
    }

}
