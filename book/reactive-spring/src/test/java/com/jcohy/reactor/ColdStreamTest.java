package com.jcohy.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:17:09
 * @since 2022.04.0
 */
public class ColdStreamTest {

    @Test
    public void cold() {
        var cold = Flux.just(1,2,3);

        StepVerifier.create(cold)
                .expectNext(1,2,3)
                .verifyComplete();

        StepVerifier.create(cold)
                .expectNext(1,2,3)
                .verifyComplete();

        var delayed = Flux.just(1,2,3)
                .delayElements(Duration.ofMillis(3));

        StepVerifier.create(delayed)
                .expectNext(1,2,3)
                .verifyComplete();

        StepVerifier.create(delayed)
                .expectNext(1,2,3)
                .verifyComplete();
    }
}
