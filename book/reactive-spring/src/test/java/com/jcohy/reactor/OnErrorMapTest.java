package com.jcohy.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:15:28
 * @since 2022.04.0
 */
public class OnErrorMapTest {

    static class GenericException extends RuntimeException {}

    @Test
    public void onErrorMap() {
        var counter = new AtomicInteger();
        Flux<Integer> resultOnError = Flux.error(new IllegalArgumentException("Oops!"));
        Flux<Integer> errorHandlingStream = resultOnError.onErrorMap(IllegalArgumentException.class, ex -> new GenericException())
                .doOnError(GenericException.class,ge -> counter.incrementAndGet());
        StepVerifier.create(errorHandlingStream)
                .expectError()
                .verify();
        Assertions.assertEquals(counter.get(),1);
    }
}
