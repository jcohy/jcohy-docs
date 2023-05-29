package rsb.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/6:14:58
 * @since 2022.04.0
 */
public class SchedulersSubscribeOnTest {

    private static final Logger log = LoggerFactory.getLogger(SchedulersSubscribeOnTest.class);

    @Test
    public void subscribeOn() {
        var rsbThreadName = SchedulersSubscribeOnTest.class.getName();
        var map = new ConcurrentHashMap<String, AtomicInteger>();

        var executor = Executors.newFixedThreadPool(5, runnable -> {
            var wrapper = (Runnable) () -> {
                var key = Thread.currentThread().getName();
                var result = map.computeIfAbsent(key, s -> new AtomicInteger());
                result.incrementAndGet();
                runnable.run();
            };
            return new Thread(wrapper, rsbThreadName);
        });

        var scheduler = Schedulers.fromExecutor(executor); // <1>
        var integerFlux = Mono.just(1)
                .subscribeOn(scheduler)
                .doFinally(signal -> map.forEach((k, v) -> log.info(k + '=' + v)));// <2>

        StepVerifier.create(integerFlux).expectNextCount(1).verifyComplete();
        var atomicInteger = map.get(rsbThreadName);
        Assertions.assertEquals(atomicInteger.get(), 1);
    }
}
