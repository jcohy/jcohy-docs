package rsb.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/6:14:51
 * @since 2022.04.0
 */
public class SchedulersHookTest {

    private static final Logger log = LoggerFactory.getLogger(SchedulersHookTest.class);

    @Test
    public void onScheduleHook() {
        var counter = new AtomicInteger();
        Schedulers.onScheduleHook("my hook",runnable -> () -> {
            var threadName = Thread.currentThread().getName();
            counter.incrementAndGet();
            log.info("before execution: " + threadName);
            runnable.run();
            log.info("after execution: " + threadName);
        });

        var integerFlux = Flux.just(1,2,3)
                .delayElements(Duration.ofMillis(1))
                .subscribeOn(Schedulers.immediate());

        StepVerifier.create(integerFlux).expectNext(1, 2, 3).verifyComplete();
        Assertions.assertEquals(3, counter.get(), "count should be 3");
    }
}
