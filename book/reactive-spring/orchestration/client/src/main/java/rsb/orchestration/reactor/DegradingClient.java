package rsb.orchestration.reactor;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:17:12
 * @since 2022.04.0
 */
@Component
public record DegradingClient(OrderClient client) {

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.client.getOrders(1,2)
                .onErrorResume(ex -> Flux.empty()) // <1>
                .subscribe(System.out::println);
    }
}
