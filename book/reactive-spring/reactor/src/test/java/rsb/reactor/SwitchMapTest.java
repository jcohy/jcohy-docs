package rsb.reactor;

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
 * @version 2022.04.0 2023/5/6:11:38
 * @since 2022.04.0
 */
public class SwitchMapTest {

    @Test
    public void switchMapWithLookaheads() {
        var source = Flux
                .just("re", "rea", "reac", "react", "reactive")
                .delayElements(Duration.ofMillis(100))
                .switchMap(this::lookup);

        StepVerifier.create(source)
                .expectNext("reactive -> reactive")
                .verifyComplete();

    }

    private Flux<String> lookup(String word) {
        return Flux.just(word + " -> reactive").delayElements(Duration.ofMillis(500));
    }
}
