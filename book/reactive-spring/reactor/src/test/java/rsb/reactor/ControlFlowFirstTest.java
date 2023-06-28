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
 * @version 2022.04.0 2023/5/8:16:07
 * @since 2022.04.0
 */
public class ControlFlowFirstTest {

    @Test
    public void first() {
        var slow = Flux.just(1,2,3).delayElements(Duration.ofMillis(200));
        var fast = Flux.just(4,5,6,7).delayElements(Duration.ofMillis(100));
        var first = Flux.firstWithSignal(slow,fast);
        StepVerifier.create(first)
                .expectNext(4,5,6,7)
                .verifyComplete();
    }
}
