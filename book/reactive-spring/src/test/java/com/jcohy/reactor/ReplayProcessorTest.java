package com.jcohy.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:17:43
 * @since 2022.04.0
 */
public class ReplayProcessorTest {

    @Test
    public void replayProcessor() {
        var historySize = 2;
        boolean unbounded = false;
//        ReplayProcessor<String> processor = ReplayProcessor.create(historySize,
//                unbounded); // <1>
//        produce(processor.sink());
//        consume(processor);

        // boot 3
        var processor = Sinks.many().replay().<String>limit(historySize);
        produce(processor);
        consume(processor.asFlux());
    }

    private void produce(FluxSink<String> sink) {
        sink.next("1");
        sink.next("2");
        sink.next("3");
        sink.complete();
    }

    // <2>
    // boot 3
    private void produce(Sinks.Many<String> sink) {
        for (var i = 0; i < 3; i++)
            sink.tryEmitNext((i + 1) + "");
        sink.tryEmitComplete();
    }

    // <3>
    private void consume(Flux<String> publisher) {
        for (int i = 0; i < 5; i++)
            StepVerifier//
                    .create(publisher)//
                    .expectNext("2")//
                    .expectNext("3")//
                    .verifyComplete();
    }
}
