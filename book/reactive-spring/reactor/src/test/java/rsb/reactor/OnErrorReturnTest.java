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
 * @version 2022.04.0 2023/5/8:15:26
 * @since 2022.04.0
 */
public class OnErrorReturnTest {

    private final Flux<Integer> resultInError = Flux.just(1,2,3)
            .flatMap(counter -> {
                if (counter == 2) {
                    return Flux.error(new IllegalArgumentException("Oops!"));
                } else {
                    return Flux.just(counter);
                }
            });

    @Test
    public void onErrorReturn() {
        var integerFlux = resultInError.onErrorReturn(0);
        StepVerifier.create(integerFlux)
                .expectNext(1,0)
                .verifyComplete();
    }
}
