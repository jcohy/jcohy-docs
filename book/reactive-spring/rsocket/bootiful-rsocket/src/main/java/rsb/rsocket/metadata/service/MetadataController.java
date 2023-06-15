package rsb.rsocket.metadata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
@Controller
public class MetadataController {

    private static final Logger log = LoggerFactory.getLogger(MetadataController.class);

    // <1>
    @ConnectMapping
    Mono<Void> setup(@Headers Map<String,Object> metadata) {
        log.info("## setup");
        return enumerate(metadata);
    }

    // <2>
    @MessageMapping("message")
    Mono<Void> message(@Header(Constants.CLIENT_ID_HEADER) String clientId,@Headers Map<String,Object> metadata) {
        log.info("## message for " + Constants.CLIENT_ID_HEADER + ' ' + clientId);
        return enumerate(metadata);
    }

    private Mono<Void> enumerate(Map<String,Object> headers) {
        headers.forEach((header,value) -> log.info(header + ":" + value));
        return Mono.empty();
    }
}
