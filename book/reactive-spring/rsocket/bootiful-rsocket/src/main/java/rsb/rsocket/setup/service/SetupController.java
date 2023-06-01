package rsb.rsocket.setup.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.routing.Customer;

import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:49
 * @since 2022.04.0
 */
// <1>
@Controller
public class SetupController {

    private static final Logger log = LoggerFactory.getLogger(SetupController.class);

    @MessageMapping("greetings.{name}")
    Mono<String> hello(@DestinationVariable String name) {
        return Mono.just("Hello, " + name + "!");
    }

    // <1>
    @MessageMapping("setup")
    public void setup(@Payload String setupPayload, @Headers Map<String,Object> headers) {
        log.info("setup payload: " + setupPayload);
        headers.forEach((k,v) -> log.info(k + '=' + v));
    }
}
