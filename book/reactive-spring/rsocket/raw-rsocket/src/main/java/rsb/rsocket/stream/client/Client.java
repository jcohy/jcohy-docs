package rsb.rsocket.stream.client;

import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
 * @version 2022.04.0 2023/5/30:16:27
 * @since 2022.04.0
 */
public record Client(BootifulProperties properties) {

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        var source = RSocketConnector.create()
                .reconnect(Retry.backoff(50, Duration.ofMillis(500)))
                .connect(TcpClientTransport.create(this.properties.getrSocket().getHostname(),
                        this.properties.getrSocket().getPort()));

        RSocketClient.from(source)
                .requestStream(Mono.just(DefaultPayload.create("Reactive Spring!"))) // <1>
                .blockLast();
    }
}
