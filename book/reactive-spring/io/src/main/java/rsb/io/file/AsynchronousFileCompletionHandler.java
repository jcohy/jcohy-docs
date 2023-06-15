package rsb.io.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:40
 * @since 2022.04.0
 */
public class AsynchronousFileCompletionHandler implements CompletionHandler<Integer, AsynchronousReadAttachment> {

    private static final Logger log = LoggerFactory.getLogger(AsynchronousFileCompletionHandler.class);

    private final ExecutorService executorService;

    private final Consumer<byte[]> handler;

    private final File source;

    private final AsynchronousFileChannel fileChannel;

    public AsynchronousFileCompletionHandler(ExecutorService executorService, Consumer<byte[]> handler,
                                             File source, AsynchronousFileChannel fileChannel) {
        this.executorService = executorService;
        this.handler = handler;
        this.source = source;
        this.fileChannel = fileChannel;
    }

    @Override
    public void completed(Integer result, AsynchronousReadAttachment attachment) {
        var byteArrayOutputStream = attachment.byteArrayOutputStream();
        if (!result.equals(-1)) {
            var buffer = attachment.buffer();
            buffer.flip();
            var storage = new byte[buffer.limit()];
            buffer.get(storage);
            try {
                byteArrayOutputStream.write(storage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            attachment.buffer().clear();
            var ra = new AsynchronousReadAttachment(source, attachment.buffer(), //
                    byteArrayOutputStream, //
                    attachment.position() + attachment.buffer().limit() //
            );
            fileChannel.read(attachment.buffer(), ra.position(), ra, this);
        } //
        else { // it's -1
            var all = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                executorService.shutdown();
            } //
            catch (Exception e) {
                error(e, attachment);
            }
            handler.accept(all);

        }
    }

    @Override
    public void failed(Throwable throwable, AsynchronousReadAttachment attachment) {
        error(throwable, attachment);
    }

    private static void error(Throwable throwable, AsynchronousReadAttachment attachment) {
        log.error("error reading file '" + attachment.source().getAbsolutePath() + "'!", throwable);
    }

}
