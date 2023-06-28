package rsb.rsocket.fireandforget.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:16:15
 * @since 2022.04.0
 */
public record Service(EncodingUtils encodingUtils, BootifulProperties properties) {

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        var transport = TcpServerTransport.create(this.properties.getrSocket().getHostname(),
                this.properties.getrSocket().getPort());

        var socket = new RSocket() {
            @Override
            public Mono<Void> fireAndForget(Payload payload) {
                var request = payload.getDataUtf8();
                log.info("received " + request + ".");
                return Mono.empty(); // <1>
            }
        };

        var socketAcceptor = SocketAcceptor.with(socket);

        RSocketServer
                .create()
                .bind(transport)
                .doOnNext( cc -> log.info("server started on the address " + cc.address()))
                .block();
    }
}
