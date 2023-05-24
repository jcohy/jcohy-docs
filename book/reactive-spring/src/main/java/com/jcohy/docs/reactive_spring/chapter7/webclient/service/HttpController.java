package com.jcohy.docs.reactive_spring.chapter7.webclient.service;

import com.jcohy.docs.reactive_spring.chapter7.webclient.client.Greeting;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;


/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:10
 * @since 2022.04.0
 */
@RestController
public class HttpController {

    // <1>
    @GetMapping("/greet/single/{name}")
    Publisher<Greeting> greetSingle(@PathVariable String name) {
        return Mono.just(greeting(name));
    }

    // <2>
    @GetMapping(value = "/greet/many/{name}" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Publisher<Greeting> greetMany(@PathVariable String name) {
        return Flux.fromStream(Stream.generate(() -> greeting(name))).delayElements(Duration.ofSeconds(1));
    }

    private Greeting greeting(String name) {
        return new Greeting("Hello " + name + " @ " + Instant.now());
    }
}
