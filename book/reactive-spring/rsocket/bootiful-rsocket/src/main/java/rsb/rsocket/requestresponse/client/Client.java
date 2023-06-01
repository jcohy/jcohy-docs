package rsb.rsocket.requestresponse.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import rsb.rsocket.metadata.Constants;

import java.util.Locale;
import java.util.UUID;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:31
 * @since 2022.04.0
 */
@Component
public record Client(RSocketRequester rSocketRequester) {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        log.info("the data mimeType is " + this.rSocketRequester.dataMimeType()); // <3>
        log.info("the metadata mimeType is " + this.rSocketRequester.metadataMimeType());
        this.rSocketRequester
                .route("greeting")// <4>
                .data("Reactive Spring") //<5>
                .retrieveMono(String.class) // <6>
                .subscribe(System.out::println);
    }
}
