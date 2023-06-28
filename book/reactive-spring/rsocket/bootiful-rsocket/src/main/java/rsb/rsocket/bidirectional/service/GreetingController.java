package rsb.rsocket.bidirectional.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.GreetingRequest;
import rsb.rsocket.GreetingResponse;
import rsb.rsocket.bidirectional.ClientHealthState;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:10:40
 * @since 2022.04.0
 */
@Controller
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    @MessageMapping("greetings")
    Flux<GreetingResponse> greetings(RSocketRequester client, @Payload GreetingRequest greetingRequest) { // <1>
        var clientHealthStateFlux = client
                .route("health") // <2>
                .data(Mono.empty())
                .retrieveFlux(ClientHealthState.class)
                .filter(chs -> chs.state().equalsIgnoreCase(ClientHealthState.STOPPED)) //<3>
                .doOnNext( chs -> log.info(chs.toString()));

        var replyPayloadFlux = Flux // <4>
                .fromStream(
                        Stream.generate(
                                () -> new GreetingResponse("Hello, " + greetingRequest.name() + " @ " + Instant.now() + "!")))
                .delayElements(Duration.ofSeconds(Math.max(3,(long) (Math.random() * 10))));

        return replyPayloadFlux
                .takeUntilOther(clientHealthStateFlux) // <5>
                .doOnNext( gr -> log.info(gr.toString()));
    }
}
