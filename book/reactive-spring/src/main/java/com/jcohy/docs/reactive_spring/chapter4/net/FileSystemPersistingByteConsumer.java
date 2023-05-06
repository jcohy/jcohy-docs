package com.jcohy.docs.reactive_spring.chapter4.net;

import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:53
 * @since 2022.04.0
 */
record FileSystemPersistingByteConsumer(String prefix) implements Consumer<byte[]> {

    private static final Logger log = LoggerFactory.getLogger(FileSystemPersistingByteConsumer.class);

    public void accept(byte[] bytes) {
        log.info("the bytes length is " + bytes.length);
        var outputDirectory = new File(new File(System.getenv("HOME"), "Desktop"), "output");
        Assert.isTrue(outputDirectory.mkdirs() || outputDirectory.exists(),
                () -> "the folder " + outputDirectory.getAbsolutePath() + " does not exist");
        var file = new File(outputDirectory, prefix + ".download");
        try {
            FileCopyUtils.copy(bytes, new FileOutputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
