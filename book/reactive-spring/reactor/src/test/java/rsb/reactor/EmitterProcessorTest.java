package rsb.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:26
 * @since 2022.04.0
 */
public class EmitterProcessorTest {

    @Test
    public void emitterProcessor() {

//        EmitterProcessor<String> processor = EmitterProcessor.create(); // <1>
//        produce(processor.sink());
//        consume(processor);

        // boot3
        var processor = Sinks.many().multicast().<String>onBackpressureBuffer();// <1>
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
        StepVerifier //
                .create(publisher)//
                .expectNext("1")//
                .expectNext("2")//
                .expectNext("3")//
                .verifyComplete();
    }

}
