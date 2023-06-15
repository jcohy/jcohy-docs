package rsb.rsocket.metadata.client;

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
        Mono<Void> one = this.rSocketRequester // <1>
                .route("message")
                .metadata(UUID.randomUUID().toString(), Constants.CLIENT_ID)
                .metadata(Locale.CHINESE.getLanguage(),Constants.LANG)
                .send();

        Mono<Void> two = this.rSocketRequester // <2>
                .route("message")
                .metadata(metadataSpec -> {
                    metadataSpec.metadata(UUID.randomUUID().toString(),Constants.CLIENT_ID);
                    metadataSpec.metadata(Locale.JAPANESE.getLanguage(),Constants.LANG);
                })
                .send();

        one.then(two).subscribe();
    }
}
