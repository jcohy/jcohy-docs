package rsb.rsocket.requestresponse.client;

import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
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
 * @version 2022.04.0 2023/5/30:15:46
 * @since 2022.04.0
 */
@Component
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);


    private final BootifulProperties properties;

    public Client(BootifulProperties properties) {
        this.properties = properties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        log.info("starting " + Client.class.getName() + ".");

        var source = RSocketConnector.create().reconnect(Retry.backoff(50, Duration.ofMillis(500)))
                .connect(TcpClientTransport.create(this.properties.getrSocket().getHostname(),
                        this.properties.getrSocket().getPort()));

        RSocketClient.from(source).requestResponse(Mono.just(DefaultPayload.create("Reactive Spring")))
                .doOnNext( d -> {
                    log.info("Received response data {} ", d.getDataUtf8());
                    d.release();
                }).repeat(10).blockLast();
    }
}
