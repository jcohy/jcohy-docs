package com.jcohy.docs.reactive_spring.chapter7.webclient.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:29
 * @since 2022.04.0
 */
public record AuthenticatedClient(WebClient client) {

    public Mono<Greeting> getAuthenticatedClient() {
        return this.client
                .get() // <1>
                .retrieve()
                .bodyToMono(Greeting.class);
    }
}
