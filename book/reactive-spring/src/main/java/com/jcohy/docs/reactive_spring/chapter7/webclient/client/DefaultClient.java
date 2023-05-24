package com.jcohy.docs.reactive_spring.chapter7.webclient.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:34
 * @since 2022.04.0
 */
public class DefaultClient {

    private final WebClient client;

    public DefaultClient(WebClient client) {
        this.client = client;
    }

    public Mono<Greeting> getSingle(String name) {
        // <1>
        return client.get()
                .uri("/greet/single/{name}", Map.of("name",name))
                .retrieve()
                .bodyToMono(Greeting.class);
    }

    public Flux<Greeting> getMany(String name) {
        // <2>
        return client
                .get()
                .uri("/greet/many/{name}",Map.of("name",name))
                .retrieve()
                .bodyToFlux(Greeting.class)
                .take(10);
    }
}
