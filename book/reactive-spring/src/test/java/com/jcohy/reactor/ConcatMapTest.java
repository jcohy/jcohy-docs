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
 * @version 2022.04.0 2023/5/6:11:30
 * @since 2022.04.0
 */
public class ConcatMapTest {

    @Test
    public void concatMap() {
        var data = Flux.just(new Pair(1, 300), new Pair(2, 200), new Pair(3, 100))
                .concatMap(id -> this.delayReplyFor(id.id, id.delay));
        StepVerifier//
                .create(data)//
                .expectNext(1, 2, 3)//
                .verifyComplete();
    }

    private Flux<Integer> delayReplyFor(Integer i, long delay) {
        return Flux.just(i).delayElements(Duration.ofMillis(delay));
    }

    private record Pair(int id, long delay) {
    }
}
