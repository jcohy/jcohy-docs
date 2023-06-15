package rsb.rsocket.metadata.client;

import io.rsocket.Payload;
import io.rsocket.core.RSocketClient;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.metadata.Constants;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:17:18
 * @since 2022.04.0
 */
public record Client(EncodingUtils encodingUtils, String clientId, BootifulProperties properties) {

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        var source = RSocketConnector
                .create()
                .reconnect(Retry.backoff(50, Duration.ofMillis(500)))
                .connect(TcpClientTransport.create(this.properties.getrSocket().getHostname(),
                        this.properties.getrSocket().getPort()));

        RSocketClient.from(source)
                .metadataPush(Mono.just(buildMetadataUpdatePayload("a-bootiful-client", Locale.JAPAN)))
                .block();
    }

    private Payload buildMetadataUpdatePayload(String clientId, Locale locale) {
        var map = Map.<String,Object>of(Constants.LANG_HEADER,locale.getLanguage(),Constants.CLIENT_ID_HEADER,clientId);

        return DefaultPayload.create("",encodingUtils.encodeMetadata(map));
    }
}
