package rsb.rsocket.security.client;

import io.rsocket.metadata.WellKnownMimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.security.GreetingResponse;

import static org.slf4j.LoggerFactory.*;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:16:46
 * @since 2022.04.0
 */
@Configuration
public class ClientConfiguration {

    private static final Logger log = getLogger(ClientConfiguration.class);

    // <1>
    private final MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

    private final UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("jlong","pw");

    // <2>
    @Bean
    RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {
        return  strategies -> strategies.encoder(new SimpleAuthenticationEncoder());
    }

    @Bean
    RSocketRequester rSocketRequester(BootifulProperties properties, RSocketRequester.Builder builder) {
        return builder.setupMetadata(this.credentials,this.mimeType) // <3>
                .tcp(properties.getrSocket().getHostname(),properties.getrSocket().getPort());
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> ready(RSocketRequester greetings) {
        return args -> greetings
                .route("greetings")
                .metadata(this.credentials,this.mimeType) // <4>
                .data(Mono.empty())
                .retrieveFlux(GreetingResponse.class)
                .subscribe(gr -> log.info("secured response: " + gr.toString()));

    }
}
