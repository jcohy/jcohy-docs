package com.jcohy.docs.reactive_spring.chapter4.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:52
 * @since 2022.04.0
 */
public class IoNetworkFileSync implements NetworkFileSync {

    public static void main(String[] args) {
        var nfs = new IoNetworkFileSync();
        nfs.start(8888, new FileSystemPersistingByteConsumer("io"));
    }


    @Override
    public void start(int port, Consumer<byte[]> consumer) {
        try(var ss = new ServerSocket(port)) {
            while (true) {
                try (var socket = ss.accept();
                     var in = socket.getInputStream();
                     var out = new ByteArrayOutputStream()) {
                    var bytes = new byte[1024];
                    var read = -1;
                    while ((read = in.read(bytes)) != -1)
                        out.write(bytes, 0, read);
                    consumer.accept(out.toByteArray());
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
