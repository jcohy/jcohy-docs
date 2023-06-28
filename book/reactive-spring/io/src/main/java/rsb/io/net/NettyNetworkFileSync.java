package rsb.io.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.AbstractEventExecutorGroup;

import java.io.IOException;
import java.util.List;
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
public class NettyNetworkFileSync implements NetworkFileSync {
    public static void main(String[] args) throws Exception {
        var nfs = new NettyNetworkFileSync();
        nfs.start(8888, new FileSystemPersistingByteConsumer("netty"));
    }

    @Override
    public void start(int port, Consumer<byte[]> bytesHandler) throws IOException, InterruptedException {

        var bossEventLoopGroup = new NioEventLoopGroup(1);
        var nioEventLoopGroup = new NioEventLoopGroup();
        var serverHandler = new NettyNetworkFileSyncServerHandler(bytesHandler);
        try {
            var serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossEventLoopGroup, nioEventLoopGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch) {
                            var channelPipeline = ch.pipeline();
                            channelPipeline.addLast(serverHandler);
                        }
                    });
            var channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } //
        finally {
            shutdown(List.of(bossEventLoopGroup, nioEventLoopGroup));
        }
    }

    private static void shutdown(List<NioEventLoopGroup> groups) {
        groups.forEach(AbstractEventExecutorGroup::shutdownGracefully);
    }
}
