package com.jcohy.docs.reactive_spring.chapter4.file;

import java.io.*;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:42
 * @since 2022.04.0
 */
class SynchronousFilesystemFileSync implements FilesystemFileSync {

    @Override
    public void start(File source, Consumer<byte[]> consumer) throws IOException {

        try (//
             var in = new BufferedInputStream(new FileInputStream(source)); //
             var out = new ByteArrayOutputStream() //
        ) {
            var read = -1;
            var bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            consumer.accept(out.toByteArray());
        }
    }

}
