package rsb.rsocket.integration.service;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import rsb.rsocket.integration.GreetingRequest;
import rsb.rsocket.integration.GreetingResponse;

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
 * @version 2022.04.0 2023/5/31:16:26
 * @since 2022.04.0
 */
@Controller
public class GreetingController {

    @MessageMapping("greetings")
    Flux<GreetingResponse> greet(GreetingRequest request) {
        return Flux
                .fromStream(
                        Stream.generate(
                                () -> new GreetingResponse("Hello, " + request.name() + " @ " + Instant.now() + "!")))
                .take(10) // <1>
                .delayElements(Duration.ofSeconds(1));
    }
}
