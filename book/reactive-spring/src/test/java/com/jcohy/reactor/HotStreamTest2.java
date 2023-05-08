package com.jcohy.reactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/6:17:08
 * @since 2022.04.0
 */
public class HotStreamTest2 {

    private static final Logger log = LoggerFactory.getLogger(HotStreamTest2.class);


    @Test
    public void hot() throws Exception {
        var factor = 10;
        log.info("start");
        var cdl = new CountDownLatch(2);
        var live = Flux.range(0,10).delayElements(Duration.ofMillis(factor)).share();
        var one = new ArrayList<Integer>();
        var two = new ArrayList<Integer>();
        live.doFinally(signalTypeConsumer(cdl)).subscribe(collect(one));

        Thread.sleep(factor * 2);
        live.doFinally(signalTypeConsumer(cdl)).subscribe(collect(two));
        cdl.await(5, TimeUnit.SECONDS);
        Assertions.assertTrue(one.size() > two.size());
        log.info("stop");
    }

    private Consumer<SignalType> signalTypeConsumer(CountDownLatch cdl) {

        return signal -> {
            if (signal.equals(SignalType.ON_COMPLETE)) {
                try {
                    cdl.countDown();
                    log.info("await()...");
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private Consumer<Integer> collect(List<Integer> ints) {
        return ints::add;
    }

}
