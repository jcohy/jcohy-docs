package rsb.io.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:17:03
 * @since 2022.04.0
 */
public class NioNetworkFileSync implements NetworkFileSync {

    public static void main(String[] args) throws Exception {
        var nfs = new NioNetworkFileSync();
        nfs.start(8888,new FileSystemPersistingByteConsumer("nio"));
    }

    @Override
    public void start(int port, Consumer<byte[]> bytesHandler) throws Exception {
        var serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8888));

        var selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (!Thread.currentThread().isInterrupted()) {
            selector.select();
            var selectionKeys = selector.selectedKeys();
            for (var it = selectionKeys.iterator(); it.hasNext();) {
                var key = it.next();
                it.remove();
                if (key.isAcceptable()) {
                    var socket = serverSocketChannel.accept();
                    accept(key, selector, socket);
                } //
                else if (key.isReadable()) {
                    read(key, selector, bytesHandler);
                }
            }
        }
    }

    record ReadAttachment(SelectionKey key, List<ByteBuffer> buffers) {
    }

    private static void read(SelectionKey key, Selector selector, Consumer<byte[]> handler) throws Exception {
        var ra = (ReadAttachment) key.attachment();
        var len = 1000;
        var bb = ByteBuffer.allocate(len);
        var channel = (SocketChannel) key.channel();
        var read = -1;

        if ((read = channel.read(bb)) >= 0) {
            ra.buffers().add(bb);
            channel.register(selector, SelectionKey.OP_READ, new ReadAttachment(ra.key(), ra.buffers()));
        }

        if (read == -1) {
            saveFile(ra.buffers(), handler);
            channel.register(selector, SelectionKey.OP_WRITE);
        }
    }

    private static void saveFile(List<ByteBuffer> buffers, Consumer<byte[]> handler) throws IOException {

        try (var baos = new ByteArrayOutputStream()) {
            for (var bb : buffers) {
                bb.flip();
                var bytes = new byte[bb.limit()];
                bb.get(bytes);
                baos.write(bytes, 0, bb.position());
            }
            var bytes = baos.toByteArray();
            handler.accept(bytes);

        }
    }

    private static void accept(SelectionKey key, Selector selector, SocketChannel socketChannel) throws IOException {
        var readAttachment = new ReadAttachment(key, new CopyOnWriteArrayList<>());
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ, readAttachment);
    }
}
