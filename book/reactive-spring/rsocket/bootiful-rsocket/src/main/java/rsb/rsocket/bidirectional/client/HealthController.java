package rsb.rsocket.bidirectional.client;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import rsb.rsocket.bidirectional.ClientHealthState;

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
 * @version 2022.04.0 2023/5/31:10:34
 * @since 2022.04.0
 */
@Controller
public class HealthController {

    @MessageMapping("health")
    Flux<ClientHealthState> health() {
        var start = new Date().getTime();

        var delayInSecond = ((long) (Math.random() * 30)) * 1000;
        return Flux
                .fromStream(Stream.generate(() -> {
                    var now = new Date().getTime();
                    var stop = ((start + delayInSecond) < now) && Math.random() > .8;
                    return new ClientHealthState(stop ? ClientHealthState.STOPPED : ClientHealthState.STARTED);
                }))
                .delayElements(Duration.ofSeconds(5));
    }
}
