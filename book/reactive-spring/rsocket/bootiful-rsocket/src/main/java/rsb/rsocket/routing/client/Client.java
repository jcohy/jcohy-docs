package rsb.rsocket.routing.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import rsb.rsocket.routing.Customer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:31
 * @since 2022.04.0
 */
@Component
public record Client(RSocketRequester rSocketRequester) {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
       this.rSocketRequester
               .route("customers.{id}", 1)
               .retrieveMono(Customer.class)
               .subscribe(c -> log.info("customers by ID:" + c));

       this.rSocketRequester
               .route("customers")
               .retrieveFlux(Customer.class)
               .subscribe(c -> log.info("customers:" + c));
    }
}
