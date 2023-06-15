package rsb.rsocket.requestresponse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import rsb.rsocket.metadata.Constants;

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
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    // <2> <3>
    @MessageMapping("greeting")
    Mono<String> greet(@Headers Map<String,Object> headers, // <4>
                       @Payload String name ) { // <5>
        headers.forEach((k,v) -> log.info(k + '=' + v));
        return Mono.just("Hello, " + name + "!");
    }
}
