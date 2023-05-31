package rsb.rsocket.bidirectional.client;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.EncodingUtils;
import rsb.rsocket.bidirectional.ClientHealthState;
import rsb.rsocket.bidirectional.GreetingRequest;
import rsb.rsocket.bidirectional.GreetingResponse;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:16:44
 * @since 2022.04.0
 */
public record Client(EncodingUtils encodingUtils,String uid,String serviceHostname, int servicePort) {

    Flux<GreetingResponse> getGreetings() {
        var greetingRequestPayload = this.encodingUtils.encode(new GreetingRequest("Client #" + this.uid));

        return RSocketConnector
                .create()
                .acceptor(new MySocketAcceptor())
                .connect(TcpClientTransport.create(this.serviceHostname,this.servicePort))
                .flatMapMany(instance -> instance
                        .requestStream(DefaultPayload.create(greetingRequestPayload))
                        .map(payload -> encodingUtils.decode(payload.getDataUtf8(), GreetingResponse.class)));
    }

    private class MySocketAcceptor implements SocketAcceptor {

        @Override
        public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
            return Mono.just(new RSocket() {
                @Override
                public Flux<Payload> requestStream(Payload payload) {
                    var start = new Date().getTime();
                    var delayInSecond = ((long) (Math.random() * 30)) * 1000;
                    var stateFlux =  Flux
                            .fromStream(Stream.generate(() -> {
                                var now = new Date().getTime();
                                var stop = ((start + delayInSecond) < now) && Math.random() > .8;
                                return new ClientHealthState(stop? ClientHealthState.STOPPED : ClientHealthState.STARTED);
                            }))
                            .delayElements(Duration.ofSeconds(5));
                    return stateFlux
                            .map(encodingUtils::encode)
                            .map(DefaultPayload::create);
                }
            });
        }
    }
}
