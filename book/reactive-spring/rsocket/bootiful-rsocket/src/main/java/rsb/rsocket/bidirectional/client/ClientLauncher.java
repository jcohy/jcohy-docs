package rsb.rsocket.bidirectional.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.GreetingResponse;

import java.time.Duration;
import java.util.stream.IntStream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:16:45
 * @since 2022.04.0
 */
@Component
public record ClientLauncher(RSocketRequester rSocketRequester) {

    private static final Logger log = LoggerFactory.getLogger(ClientLauncher.class);

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        var maxClients = 10;
        var nestedMax = Math.max(5,(int) (Math.random() * maxClients));
        log.info("launching " + nestedMax + " clients.");

        Flux.fromStream(IntStream.range(0,nestedMax).boxed()) // <2>
                .map(id -> new Client(this.rSocketRequester,Long.toString(id))) // <3>
                .flatMap(client -> Flux.just(client).delayElements(Duration.ofSeconds((long) (30 * Math.random())))) // <4>
                .flatMap(Client::getGreetings) // <5>
                .map(GreetingResponse::toString) // <6>
                .subscribe(log::info);
    }
}
