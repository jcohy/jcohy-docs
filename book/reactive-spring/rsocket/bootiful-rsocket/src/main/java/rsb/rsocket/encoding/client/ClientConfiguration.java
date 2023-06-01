package rsb.rsocket.encoding.client;

import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import rsb.rsocket.BootifulProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:13
 * @since 2022.04.0
 */
@Configuration
public class ClientConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {
        return strategies -> strategies
                .decoder(new Jackson2JsonDecoder())
                .encoder(new Jackson2JsonEncoder());
    }

    @Bean
    RSocketRequester rSocketRequester(BootifulProperties properties, RSocketRequester.Builder builder) {
        return builder.tcp(properties.getrSocket().getHostname(),properties.getrSocket().getPort());
    }
}
