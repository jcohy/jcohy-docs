package rsb.rsocket.bidirectional.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.util.DefaultPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.BootifulProperties;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.bidirectional.ClientHealthState;
import rsb.rsocket.bidirectional.GreetingRequest;
import rsb.rsocket.bidirectional.GreetingResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:16:58
 * @since 2022.04.0
 */
@Component
public class Service implements SocketAcceptor {

    private static final Logger log = LoggerFactory.getLogger(Service.class);

    private final BootifulProperties properties;

    private final EncodingUtils encodingUtils;

    public Service(BootifulProperties properties, EncodingUtils encodingUtils) {
        this.properties = properties;
        this.encodingUtils = encodingUtils;
    }

    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket clientSocket) {

        // <1>
        return Mono.just(new RSocket() {
            @Override
            public Flux<Payload> requestStream(Payload payload) {

                // <2>
                var clientHealthStateFlux = clientSocket
                        .requestStream(DefaultPayload.create(new byte[0]))
                        .map(p -> encodingUtils.decode(p.getDataUtf8(), ClientHealthState.class))
                        .filter(chs -> chs.state().equalsIgnoreCase(ClientHealthState.STOPPED));

                // <3>
                var replyPayloadFlux = Flux
                        .fromStream(Stream.generate(() -> {
                            var greetingRequest = encodingUtils.decode(payload.getDataUtf8(), GreetingRequest.class);
                            var message = "Hello, " + greetingRequest.name() + " @ " + Instant.now() + "!";
                            return new GreetingResponse(message);
                        }))
                        .delayElements(Duration.ofSeconds(Math.max(3,(long) (Math.random() * 10))))
                        .doFinally(signalType -> log.info("finished."));

                return replyPayloadFlux
                        .takeUntilOther(clientHealthStateFlux)
                        .map(encodingUtils::encode)
                        .map(DefaultPayload::create);
            }
        });
    }
}
