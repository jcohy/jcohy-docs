package com.jcohy.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:15:09
 * @since 2022.04.0
 */
public class ContextTest {

    private static final Logger log = LoggerFactory.getLogger(ContextTest.class);

    @Test
    public void context() throws InterruptedException {
        var observedContextValues = new ConcurrentHashMap<String, AtomicInteger>();

        var max = 3;
        var key = "key1";
        var cdl = new CountDownLatch(max);

        var context = Context.of(key,"value1");

        var just = Flux.range(0,max)
                .delayElements(Duration.ofMillis(1))
                .doOnEach((Signal<Integer> integerSignal) -> { // <1>
                    var currentContext = integerSignal.getContextView();
                    if(integerSignal.getType().equals(SignalType.ON_NEXT)) {
                        String key1 = context.get(key);
                        Assertions.assertNotNull(key1);
                        Assertions.assertEquals(key1,"value1");
                        observedContextValues.computeIfAbsent(key1, k -> new AtomicInteger(0)).incrementAndGet();
                    }
                })
                .contextWrite(context);

        just.subscribe(integer -> {
            log.info("integer: " + integer);
            cdl.countDown();
        });

        cdl.await();

        Assertions.assertEquals(observedContextValues.get(key),max);
    }

}
