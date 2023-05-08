package com.jcohy.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:17:00
 * @since 2022.04.0
 */
public class AsyncApiIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(AsyncApiIntegrationTest.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Test
    public void async() {
        var integers = Flux.<Integer>create(emitter -> this.launch(emitter,5));
        StepVerifier
                .create(integers.doFinally(signalType -> this.executorService.shutdown()))
                .expectNextCount(5)
                .verifyComplete();
    }

    private void launch(FluxSink<Integer> integetFluxSink, int count) {
        this.executorService.submit(() -> {
            var integer = new AtomicInteger();
            Assertions.assertNotNull(integetFluxSink);
            while (integer.get() < count) {
                var random = Math.random();
                integetFluxSink.next(integer.incrementAndGet());
                this.sleep((long)(random * 1_000));
            }
        });
    }

    private void sleep(long s) {

        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            log.error("something's wrong!");
        }
    }

}
