package rsb.rsocket.errors.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:25
 * @since 2022.04.0
 */
@Controller
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @MessageMapping("greetings")
    Flux<String> greet(String name) { // <1>
        return Flux
                .fromStream(Stream.generate(() -> "Hello, " + name + "!"))
                .flatMap(message -> {
                    if(Math.random() >= .3) {
                        return Mono.error(new IllegalArgumentException("Oops!"));
                    }
                    else {
                        return Mono.just(message);
                    }
                })
                .delayElements(Duration.ofSeconds(1));
    }


    @MessageExceptionHandler // <2>
    void exception(Exception exception) {
        log.error("the exception is " + exception.getMessage());
    }
}
