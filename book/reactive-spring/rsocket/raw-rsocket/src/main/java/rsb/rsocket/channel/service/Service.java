package rsb.rsocket.channel.service;

import io.rsocket.Payload;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;
import rsb.rsocket.BootifulProperties;

import java.time.Duration;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:17:39
 * @since 2022.04.0
 */
@Component
public record Service(BootifulProperties properties) {

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        var socketAcceptor = SocketAcceptor
                .forRequestChannel(payloads -> Flux.from(payloads) // <1>
                        .map(Payload::getDataUtf8) // <2>
                        .map(s -> "Echo: " + s)
                        .map(DefaultPayload::create)
                );

        RSocketServer
                .create(socketAcceptor)
                .bind(TcpServerTransport.create(this.properties.getrSocket().getHostname()
                    ,this.properties.getrSocket().getPort()))
                .doOnNext(cc -> log.info("server started on the address " + cc.address()))
                .block();

    }
}
