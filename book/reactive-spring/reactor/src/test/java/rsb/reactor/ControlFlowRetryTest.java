package rsb.reactor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:15:35
 * @since 2022.04.0
 */
public class ControlFlowRetryTest {

    private static final Logger log = LoggerFactory.getLogger(ControlFlowRetryTest.class);

    @Test
    public void retry() {
        var errored = new AtomicBoolean();

       var producer = Flux.<String>create(sink -> {
            if(!errored.get()) {
                errored.set(true);
                sink.error(new RuntimeException("Nope!"));
                log.info("returning a " + RuntimeException.class.getName() + "!");
            }
            else {
                log.info("we've already errored so here's the value");
                sink.next("hello");
            }
            sink.complete();
        });

       var retryOnError = producer.retry();
        StepVerifier.create(producer)
                .expectNext("hello")
                .verifyComplete();
    }
}
