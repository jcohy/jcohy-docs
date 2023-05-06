package com.jcohy.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/6:10:57
 * @since 2022.04.0
 */
public class TransformTest {

    @Test
    public void transform() {
        var finished = new AtomicBoolean();
        var letters = Flux
                .just("A", "B", "C")
                .transform(stringFlux -> stringFlux.doFinally(signal -> finished.set(true))); // <1>

        StepVerifier.create(letters)
                .expectNextCount(3)
                .verifyComplete();

        Assertions.assertTrue(finished.get(), "the finished Boolean must be true.");
    }
}
