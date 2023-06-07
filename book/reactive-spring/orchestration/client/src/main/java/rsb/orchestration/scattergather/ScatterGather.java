package rsb.orchestration.scattergather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rsb.orchestration.Customer;
import rsb.orchestration.Order;
import rsb.orchestration.TimerUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:15:39
 * @since 2022.04.0
 */
@Component
public record ScatterGather(CrmClient client) {

    private static final Logger log = LoggerFactory.getLogger(ScatterGather.class);

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        var ids = new Integer[]{1,2,7,5}; // <1>

        Flux<Customer> customerFlux = TimerUtils.cache(client.getCustomers(ids));
        Flux<Order> orderFlux = TimerUtils.cache(client.getOrders(ids));
        Flux<CustomerOrders> customerOrdersFlux = customerFlux
                .flatMap(customer -> { // <3>

                    // <4>
                    var monoOfListOfOrders = orderFlux
                            .filter(o -> o.customerId().equals(customer.id()))
                            .collectList();

                    // <5>
                    var profileMono = client.getProfile(customer.id());

                    // <6>
                    var customerMono = Mono.just(customer);

                    // <7>
                    return Flux.zip(customerMono,monoOfListOfOrders,profileMono);

                }) // <8>
                .map(tuple -> new CustomerOrders(tuple.getT1(),tuple.getT2(),tuple.getT3()));

        for (var i = 0; i< 5 ; i++) { // <9>
            run(customerOrdersFlux);
        }
    }


    private void run(Flux<CustomerOrders> customerOrdersFlux) {
        TimerUtils
                .monitor(customerOrdersFlux)
                .subscribe(customerOrders -> {
                    log.info("-------------------");
                    log.info(customerOrders.customer().toString());
                    log.info(customerOrders.profile().toString());
                    customerOrders.orders().forEach(order -> log.info(customerOrders.customer().id() + ": " + order));
                });
    }
}
