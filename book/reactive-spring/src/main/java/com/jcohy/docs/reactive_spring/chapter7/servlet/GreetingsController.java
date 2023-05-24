package com.jcohy.docs.reactive_spring.chapter7.servlet;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:00
 * @since 2022.04.0
 */
@RestController
public class GreetingsController {

    @GetMapping(value = "/hello/controller/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<Greetings> greet(ServerHttpRequest request, @PathVariable String name) {
        return Greetings.greet("controller",name);
    }
}
