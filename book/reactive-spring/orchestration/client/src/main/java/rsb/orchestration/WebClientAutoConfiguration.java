package rsb.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:17:01
 * @since 2022.04.0
 */
@Configuration
public class WebClientAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebClientAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    WebClient loadBalancingWebClient(WebClient.Builder builder, LoadBalancedExchangeFilterFunction filter) { // <1>
        log.info("registering a default load-balanced " +  WebClient.class.getName() + ".");
        return builder.filter(filter).build();
    }
}
