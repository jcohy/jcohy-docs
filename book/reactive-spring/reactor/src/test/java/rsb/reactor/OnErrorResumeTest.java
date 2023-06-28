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
 * @version 2022.04.0 2023/5/8:15:21
 * @since 2022.04.0
 */
public class OnErrorResumeTest {

    private final Flux<Integer> resultInError = Flux.just(1,2,3)
            .flatMap(counter -> {
              if (counter == 2) {
                  return Flux.error(new IllegalArgumentException("Oops!"));
              } else {
                  return Flux.just(counter);
              }
            });

    @Test
    public void onErrorResume() {
        Flux<Integer> integerFlux = resultInError
                .onErrorResume(IllegalArgumentException.class, e -> Flux.just(3,2,1));
        StepVerifier.create(integerFlux)
                .expectNext(1,3,2,1)
                .verifyComplete();
    }
}
