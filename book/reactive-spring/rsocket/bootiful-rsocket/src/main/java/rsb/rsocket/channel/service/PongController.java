package rsb.rsocket.channel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:08
 * @since 2022.04.0
 */
@Controller
public class PongController {

    private static final Logger log = LoggerFactory.getLogger(PongController.class);

    @MessageMapping("pong")
    Flux<String> pong(@Payload Flux<String> ping) {
        return ping.map(request -> "pong")
                .doOnNext(log::info);
    }
}
