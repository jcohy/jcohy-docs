package rsb.reactor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/8:16:45
 * @since 2022.04.0
 */
public class BlockhoundTest {

    private final static AtomicBoolean BLOCKHOUND = new AtomicBoolean();

    @BeforeEach
    public void before() {

        BLOCKHOUND.set(true);

        var integerations = new ArrayList<BlockHoundIntegration>();
        var services = ServiceLoader.load(BlockHoundIntegration.class);

        services.forEach(integerations::add);

        integerations.add(builder -> builder.blockingMethodCallback(blockingMethod -> {
            if (BLOCKHOUND.get()) {
                throw new BlockingCallError(blockingMethod.toString());
            }
        }));

        BlockHound.install(integerations.toArray(new BlockHoundIntegration[0]));
    }

    @Test
    public void notOk() {
        StepVerifier
                .create(this.buildBlockingMono().subscribeOn(Schedulers.parallel()))
                .expectErrorMatches( e -> e instanceof BlockingCallError)
                .verify();
    }

    @Test
    public void ok() {
        StepVerifier
                .create(this.buildBlockingMono().subscribeOn(Schedulers.boundedElastic()))
                .expectNext(1L)
                .verifyComplete();
    }

    @AfterEach
    public void after() {
        BLOCKHOUND.set(false);
    }


    Mono<Long> buildBlockingMono() {
        return Mono.just(1L)
                .doOnNext(it -> block());
    }

    void block() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class BlockingCallError extends Error {
        public BlockingCallError(String msg) {
            super(msg);
        }
    }
}
