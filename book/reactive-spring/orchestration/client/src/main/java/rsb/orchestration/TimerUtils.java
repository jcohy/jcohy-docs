package rsb.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:11:34
 * @since 2022.04.0
 */
public abstract class TimerUtils {

    private static final Logger log = LoggerFactory.getLogger(TimerUtils.class);

    // <1>
    public static <T> Mono<T> cache(Mono<T> cache) {
        return cache.doOnNext(c -> log.debug("receiving " + c.toString())).cache();
    }

    public static <T> Flux<T> cache(Flux<T> cache) {
        return cache.doOnNext(c -> log.debug("receiving " + c.toString())).cache();
    }

    // <2>
    public static <T> Mono<T> monitor(Mono<T> configMono) {
        var start = new AtomicLong();
        return configMono
                .doOnError(exception -> log.error("oops!", exception))
                .doOnSubscribe((subscription -> start.set(System.currentTimeMillis())))
                .doOnNext((greeting) -> log.info("total time : {}",System.currentTimeMillis() - start.get()));
    }

    public static <T> Flux<T> monitor(Flux<T> configMono) {
        var start = new AtomicLong();
        return configMono
                .doOnError(exception -> log.error("oops!", exception))
                .doOnSubscribe((subscription -> start.set(System.currentTimeMillis())))
                .doOnNext((greeting) -> log.info("total time : {}",System.currentTimeMillis() - start.get()));
    }
}
