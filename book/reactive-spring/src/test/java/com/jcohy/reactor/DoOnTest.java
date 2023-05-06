package com.jcohy.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/6:11:53
 * @since 2022.04.0
 */
public class DoOnTest {

    private static final Logger log = LoggerFactory.getLogger(DoOnTest.class);

    @Test
    public void doOn() {
        var signals = new ArrayList<Signal<Integer>>();
        var nextValues = new ArrayList<Integer>();
        var subscriptions = new ArrayList<Subscription>();
        var exceptions = new ArrayList<Throwable>();
        var finallySignals = new ArrayList<SignalType>();

        Flux<Integer> on = Flux
                .<Integer>create(sink -> {
                    sink.next(1);
                    sink.next(2);
                    sink.next(3);
                    sink.error(new IllegalArgumentException("oops!"));
                    sink.complete();
                })
                .doOnNext(nextValues::add)
                .doOnEach(signals::add)
                .doOnSubscribe(subscriptions::add)
                .doOnError(exceptions::add)
                .doFinally(finallySignals::add);

        StepVerifier
                .create(on)
                .expectNext(1,2,3)
                .expectError(IllegalArgumentException.class)//
                .verify();

        signals.forEach(this::info);
        Assertions.assertEquals(4, signals.size());

        finallySignals.forEach(this::info);
        Assertions.assertEquals(finallySignals.size(), 1);

        subscriptions.forEach(this::info);
        Assertions.assertEquals(subscriptions.size(), 1);

        exceptions.forEach(this::info);
        Assertions.assertEquals(exceptions.size(), 1);
        Assertions.assertTrue(exceptions.get(0) instanceof IllegalArgumentException);

        nextValues.forEach(this::info);
        Assertions.assertEquals(Arrays.asList(1, 2, 3), nextValues);
    }

    private <T> void info(Object signalType) {
        log.info(signalType.toString());
    }
}
