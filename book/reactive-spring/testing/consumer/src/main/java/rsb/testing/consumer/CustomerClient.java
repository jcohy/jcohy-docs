package rsb.testing.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import rsb.testing.consumer.Customer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:15:52
 * @since 2022.04.0
 */
@Component
public class CustomerClient {

    private static final Logger log = LoggerFactory.getLogger(CustomerClient.class);

    public CustomerClient(WebClient client) {
        this.client = client;
    }

    private final WebClient client;

    private String base = "localhost:8080";

    public void setBase(String base) {
        this.base = base;

    }

    public Flux<Customer> getAllCustomer() {
        return  this.client // <1>
                .get() // <2>
                .uri("http://" + this.base + "/customers") // <3>
                .retrieve() // <4>
                .bodyToFlux(Customer.class); // <5>
    }
}
