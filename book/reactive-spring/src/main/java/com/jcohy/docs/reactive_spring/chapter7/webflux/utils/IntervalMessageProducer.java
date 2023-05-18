package com.jcohy.docs.reactive_spring.chapter7.webflux.utils;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:11:28
 * @since 2022.04.0
 */
public abstract class IntervalMessageProducer {

    public static Flux<String> produce(int c) {
        return produce().take(c);
    }

    public static Flux<String> produce() {
        return doProduceCountAndStrings().map(CountAndString::message);
    }

    private static Flux<CountAndString> doProduceCountAndStrings() {
        var counter = new AtomicLong();
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> new CountAndString(counter.incrementAndGet()));
    }
}

record CountAndString(String message,long count) {
    CountAndString(long count) {
        this("#" + count , count);
    }
}