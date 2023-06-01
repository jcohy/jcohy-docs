package rsb.rsocket.channel.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:04
 * @since 2022.04.0
 */
@Component
public class Client implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private final RSocketRequester rSocketRequester;

    public Client(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var ping = Flux
                .interval(Duration.ofSeconds(1))
                .map( i -> "ping");

        rSocketRequester
                .route("pong")
                .data(ping)
                .retrieveFlux(String.class)
                .subscribe(log::info);
    }
}
