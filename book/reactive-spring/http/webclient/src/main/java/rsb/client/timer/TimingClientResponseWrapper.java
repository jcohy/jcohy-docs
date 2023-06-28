package rsb.client.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:57
 * @since 2022.04.0
 */
public class TimingClientResponseWrapper extends ClientResponseWrapper {

    private static final Logger log = LoggerFactory.getLogger(TimingClientResponseWrapper.class);
    /**
     * Create a new {@code ClientResponseWrapper} that wraps the given response.
     *
     * @param delegate the response to wrap
     */
    public TimingClientResponseWrapper(ClientResponse delegate) {
        super(delegate);
    }

    private void start() {
        log.info("start @ " + Instant.now().toString());
    }

    private void stop() {
        log.info("stop @ " + Instant.now());
    }

    // <1>
    private <T> Mono<T> log(Mono<T> c) {
        return c.doOnSubscribe( s -> start()).doFinally(s -> stop());
    }

    private <T> Flux<T> log(Flux<T> c) {
        return c.doOnSubscribe(s -> start()).doFinally(s -> stop());
    }

    // <2>
    @Override
    public <T> T body(BodyExtractor<T, ? super ClientHttpResponse> extractor) {
        T body = super.body(extractor);

        if (body instanceof Flux f) {
            return (T) log(f);
        }
        if (body instanceof Mono m) {
            return (T) log(m);
        }
        return body;
    }

    @Override
    public <T> Mono<T> bodyToMono(Class<? extends T> elementClass) {

        return log(super.bodyToMono(elementClass));
    }

    @Override
    public <T> Mono<T> bodyToMono(ParameterizedTypeReference<T> elementTypeRef) {
        return log(super.bodyToMono(elementTypeRef));
    }

    @Override
    public <T> Flux<T> bodyToFlux(Class<? extends T> elementClass) {
        return log(super.bodyToFlux(elementClass));
    }

    @Override
    public <T> Flux<T> bodyToFlux(ParameterizedTypeReference<T> elementTypeRef) {
        return log(super.bodyToFlux(elementTypeRef));
    }
}
