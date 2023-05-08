package com.jcohy.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:15:59
 * @since 2022.04.0
 */
public class ControlFlowTimeoutTest {

    @Test
    public void timeout() {
        var ids = Flux.just(1,2,3)
                .delayElements(Duration.ofSeconds(1))
                .timeout(Duration.ofMillis(500))
                .onErrorResume(this::given);

        StepVerifier.create(ids)
                .expectNext(0)
                .verifyComplete();
    }

    public Flux<Integer> given(Throwable t) {
        Assertions.assertTrue(t instanceof TimeoutException,"this exception should be a " + TimeoutException.class.getName());
        return Flux.just(0);
    }
}
