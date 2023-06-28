package rsb.rsocket.setup.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.util.retry.Retry;
import rsb.rsocket.BootifulProperties;

import java.time.Duration;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:33
 * @since 2022.04.0
 */
@Configuration
public class ClientConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ClientConfiguration.class);

    @Bean
    ApplicationRunner applicationRunner(RSocketRequester rSocketRequester) {
        return args -> rSocketRequester
                .route("greetings.{name}","World")
                .retrieveMono(String.class)
                .subscribe(log::info);
    }
    @Bean
    RSocketRequester rSocketRequester(BootifulProperties properties, RSocketRequester.Builder builder) {
        return builder
                .setupData("setup data") // <1>
                .setupRoute("setup") // <2>
                .rsocketConnector(
                        connector -> connector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2)))
                )
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp(properties.getrSocket().getHostname(),properties.getrSocket().getPort()); // <3>
    }
}
