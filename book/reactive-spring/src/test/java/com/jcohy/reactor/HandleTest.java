package com.jcohy.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/6:14:10
 * @since 2022.04.0
 */
public class HandleTest {


    @Test
    public void handle() {

        StepVerifier//
                .create(this.handle(5, 4))//
                .expectNext(0, 1, 2, 3)//
                .expectError(IllegalArgumentException.class)//
                .verify();

        StepVerifier//
                .create(this.handle(3, 3))//
                .expectNext(0, 1, 2)//
                .verifyComplete();
    }

    Flux<Integer> handle(int max, int numberToError) {
        return Flux.range(0,max) // <1>
                .handle((value,sink) -> {
                    var upTo = Stream.iterate(0,i -> i < numberToError, i -> i + 1)
                            .collect(Collectors.toList());
                    if (upTo.contains(value)) {
                        sink.next(value);
                        return;
                    }
                    if (value == numberToError) {
                        sink.error(new IllegalArgumentException("No 4 for you!"));
                        return;
                    }
                    sink.complete();
                });
    }
}
