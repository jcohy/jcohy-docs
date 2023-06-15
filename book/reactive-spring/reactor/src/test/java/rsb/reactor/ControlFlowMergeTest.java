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
 * @version 2022.04.0 2023/5/8:15:47
 * @since 2022.04.0
 */
public class ControlFlowMergeTest {

    @Test
    public void merge() {
        var fastest = Flux.just(5,6);
        var secondFastest = Flux.just(1,2).delayElements(Duration.ofMillis(2));
        var thirdFastest = Flux.just(3,4).delayElements(Duration.ofMillis(20));
        var streamOfStreams = Flux.just(secondFastest,thirdFastest,fastest);
        var merge = Flux.merge(streamOfStreams);

        StepVerifier.create(merge)
                .expectNext(5,6,1,2,3,4)
                .verifyComplete();
    }
}
