package com.jcohy.docs.reactive_spring.chapter4.net;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:17:07
 * @since 2022.04.0
 */
@ChannelHandler.Sharable
class NettyNetworkFileSyncServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(NettyNetworkFileSyncServerHandler.class);

    private final Consumer<byte[]> consumer;

    private final AtomicReference<ByteArrayOutputStream> byteArrayOutputStream = new AtomicReference<>(
            new ByteArrayOutputStream());

    NettyNetworkFileSyncServerHandler(Consumer<byte[]> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        if (msg instanceof AbstractReferenceCountedByteBuf buf) {
            var bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            this.byteArrayOutputStream.get().write(bytes);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
        var baos = this.byteArrayOutputStream.get();
        if (null != baos) {
            try {
                var bytes = baos.toByteArray();
                if (bytes.length != 0) {
                    this.consumer.accept(bytes);
                }
                // we've read the bytes,
                // time to reset for a new request
                this.byteArrayOutputStream.set(new ByteArrayOutputStream());
            } //
            finally {
                ctx.flush();
                baos.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("oh no!", cause);
        ctx.close();
    }

}
