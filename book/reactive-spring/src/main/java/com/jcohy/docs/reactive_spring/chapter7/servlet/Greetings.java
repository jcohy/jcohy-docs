package com.jcohy.docs.reactive_spring.chapter7.servlet;

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
public record Greetings(String message) {
    static Mono<Greetings> greet(String contextPath, String name) {
        return Mono.just(new Greetings("Hello, " + name + " from " + contextPath + "!"));
    }
}
