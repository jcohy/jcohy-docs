package rsb.rsocket.bidirectional.client;

import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import rsb.rsocket.GreetingRequest;
import rsb.rsocket.GreetingResponse;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:10:30
 * @since 2022.04.0
 */
public record Client(RSocketRequester rSocketRequester,String uuid) {

    Flux<GreetingResponse> getGreetings() {
        return rSocketRequester()
                .route("greetings")
                .data(new GreetingRequest("client # " + this.uuid))
                .retrieveFlux(GreetingResponse.class);

    }
}
