package com.jcohy.docs.reactive_spring.chapter7.webclient.client;

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
 * @version 2022.04.0 2023/5/24:15:37
 * @since 2022.04.0
 */
@Configuration
public class DefaultConfiguration {

    @Bean
    DefaultClient defaultClient(WebClient.Builder builder, ClientProperties clientProperties) {
        var root = clientProperties.getHttp().getRootUrl();
        return new DefaultClient(builder.baseUrl(root).build()); // <1>
    }
}
