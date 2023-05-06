package com.jcohy.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/6:11:14
 * @since 2022.04.0
 */
public class MapTest {

    @Test
    public void maps() {
        var data = Flux.just("a","b","c").map(String::toUpperCase);

        StepVerifier.create(data)
                .expectNext("A","B","C")
                .verifyComplete();
    }
}
