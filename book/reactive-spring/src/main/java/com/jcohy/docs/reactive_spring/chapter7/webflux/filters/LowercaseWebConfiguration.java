package com.jcohy.docs.reactive_spring.chapter7.webflux.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:15:11
 * @since 2022.04.0
 */
@Configuration
public class LowercaseWebConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LowercaseWebConfiguration.class);

    @Bean
    RouterFunction<ServerResponse> responseRouterFunction() {
        var uuidKey = UUID.class.getName();

        return route() // <1>
                .GET("/hi/{name}",this::handler)
                .GET("/hello/{name}",this::handler)
                .filter((req,next) -> { // <2>
                    log.info(".filter(): before");
                    var reply = next.handle(req);
                    log.info(".filter(): after");
                    return reply;
                })
                .before(request -> {
                    log.info(".before()"); // <3>
                    request.attributes().put(uuidKey,UUID.randomUUID());
                    return request;
                })
                .after(((request, serverResponse) -> {
                    log.info(".after()"); // <4>
                    log.info("UUID: " + request.attributes().get(uuidKey));
                    return serverResponse;
                }))
                .onError(NullPointerException.class,(e,request) -> badRequest().build())
                .build();
    }

    private Mono<ServerResponse> handler(ServerRequest request) {
        return ok().bodyValue(String.format("Hello %s",request.pathVariable("name")));
    }
}
