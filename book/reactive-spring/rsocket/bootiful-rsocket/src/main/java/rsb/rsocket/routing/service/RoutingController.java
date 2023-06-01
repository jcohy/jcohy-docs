package rsb.rsocket.routing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.rsocket.routing.Customer;

import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:49
 * @since 2022.04.0
 */
// <1>
@Controller
public class RoutingController {

    private static final Logger log = LoggerFactory.getLogger(RoutingController.class);

    private final Map<Integer, Customer> customers = Map.of(1,new Customer(1,"Zhen"),
            2,new Customer(2,"Zhouyue"));
    // <2>
    @MessageMapping("customers")
    Flux<Customer> all() { // <5>
        return Flux.fromStream(this.customers.values().stream());
    }

    // <3>
    @MessageMapping("customers.{id}")
    Mono<Customer> byId(@DestinationVariable Integer id) {
        return Mono.justOrEmpty(this.customers.get(id));
    }
}
