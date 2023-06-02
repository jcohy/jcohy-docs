package rsb.rsocket.security.service;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.security.GreetingRequest;
import rsb.rsocket.security.GreetingResponse;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:16:52
 * @since 2022.04.0
 */
@Controller
public class GreetingsController {

    @MessageMapping("greetings")
    Flux<GreetingResponse> greet(@AuthenticationPrincipal Mono<UserDetails> user) { // <1>
        return user
                .map(UserDetails::getUsername)
                .map(GreetingRequest::new)
                .flatMapMany(this::greet);
    }

    private Flux<GreetingResponse> greet(GreetingRequest request) {
        return Flux
                .fromStream(Stream.generate( () -> new GreetingResponse("Hello, " + request.name() + "!")))
                .delayElements(Duration.ofSeconds(1));
    }
}
