package rsb.rsocket.requestresponse.service;

import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:15:51
 * @since 2022.04.0
 */
@Component
public record Service(BootifulProperties properties) {

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        var transport = TcpServerTransport.create(properties.getrSocket().getHostname(),
                properties.getrSocket().getPort());

        RSocketServer
                .create(SocketAcceptor.forRequestResponse( p -> Mono.just(DefaultPayload.create("Hello, " + p.getDataUtf8()))))
                .bind(transport)
                .doOnNext( cc ->
                    log.info("server started on the address " + cc.address()))
                .block();
    }
}
