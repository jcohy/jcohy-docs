package com.jcohy.java.closeable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    static String CONTENTS = String.format("""
            <html>
            <body><h1> Hello, world, @ %s ! </h1></body>
            </html>
            """, Instant.now().toString().trim());

    static File setup() {
        try {
            var path = Files.createTempFile("rsb", ".txt");
            var file = path.toFile();
            file.deleteOnExit();
            Files.writeString(path,CONTENTS);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void error(Throwable throwable) {
        System.out.println("there's been an exception processing the read!" + throwable);
    }
}
