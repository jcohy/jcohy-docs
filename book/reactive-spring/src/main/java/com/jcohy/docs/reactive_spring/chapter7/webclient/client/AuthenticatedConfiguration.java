package com.jcohy.docs.reactive_spring.chapter7.webclient.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:31
 * @since 2022.04.0
 */
@Configuration
public class AuthenticatedConfiguration {

    @Bean
    AuthenticatedClient authenticatedClient(WebClient.Builder builder, ClientProperties clientProperties) {
        // <1>
        var httpProperties = clientProperties.getHttp();
        var basicAuthProperties = clientProperties.getHttp().getBasic();

        // <2>
        var filterFunction = ExchangeFilterFunctions.basicAuthentication(basicAuthProperties.getUsername(),
                basicAuthProperties.getPassword());

        // <3>
        WebClient client = builder
                .baseUrl(httpProperties.getRootUrl())
                .filters(filters -> filters.add(filterFunction))
                .build();
        return new AuthenticatedClient(client);
    }
}
