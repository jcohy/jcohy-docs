package com.jcohy.docs.reactive_spring.chapter7.webflux.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:15:06
 * @since 2022.04.0
 */
@Component
public class LowercaseWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange currentRequest, WebFilterChain chain) {

        // <1>
        var lowercaseUri = URI.create(currentRequest.getRequest().getURI().toString().toLowerCase());

        var outgoingExchange = currentRequest.mutate() // <2>
                .request(builder -> builder.uri(lowercaseUri)).build();
        return chain.filter(outgoingExchange);
    }
}
