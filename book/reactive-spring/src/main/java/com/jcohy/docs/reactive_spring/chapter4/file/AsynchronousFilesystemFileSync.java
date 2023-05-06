package com.jcohy.docs.reactive_spring.chapter4.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:39
 * @since 2022.04.0
 */
public class AsynchronousFilesystemFileSync implements FilesystemFileSync {
    @Override
    public void start(File source, Consumer<byte[]> handler) throws IOException {
        // <1>
        var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        // <2>
        var fileChannel = AsynchronousFileChannel.open(source.toPath(), Collections.singleton(StandardOpenOption.READ),
                executorService);
        // <3>
        var completionHandler = new AsynchronousFileCompletionHandler(executorService, handler, source, fileChannel);
        var attachment = new AsynchronousReadAttachment(source, ByteBuffer.allocate(1024), new ByteArrayOutputStream(),
                0);
        fileChannel.read(attachment.buffer(), attachment.position(), attachment, completionHandler);
    }
}
