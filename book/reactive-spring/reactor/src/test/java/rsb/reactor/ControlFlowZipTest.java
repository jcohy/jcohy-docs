package rsb.reactor;

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
 * @version 2022.04.0 2023/5/8:15:53
 * @since 2022.04.0
 */
public class ControlFlowZipTest {

    @Test
    public void zip() {
        var first = Flux.just(1,2,3);
        var second = Flux.just("a","b","c");
        var zip = Flux.zip(first, second)
                .map(tuple ->
                    this.from(tuple.getT1(),tuple.getT2()));

        StepVerifier.create(zip)
                .expectNext("1:a","2:b","3:c")
                .verifyComplete();
    }

    private String from(Integer i ,String s) {
        return i + ":" + s;
    }
}
