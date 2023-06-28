package rsb.rsocket.encoding.service;

import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:19
 * @since 2022.04.0
 */
@Configuration
public class ServiceConfiguration {

    // <1>
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    RSocketStrategiesCustomizer rSocketStrategiesCustomizer() { // <2>
        return strategies -> strategies
                .decoder(new Jackson2JsonDecoder()) // <3>
                .encoder(new Jackson2JsonEncoder());
    }
}
