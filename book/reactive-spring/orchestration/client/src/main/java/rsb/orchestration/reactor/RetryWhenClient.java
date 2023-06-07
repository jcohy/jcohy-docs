package rsb.orchestration.reactor;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:17:14
 * @since 2022.04.0
 */
public record RetryWhenClient(OrderClient client) {

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.client.getOrders(1,2)
                .retryWhen(Retry.backoff(10, Duration.ofSeconds(1))) // <1>
                .subscribe(System.out::println);
    }
}
