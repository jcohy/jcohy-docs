package rsb.http.customers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;


/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:11:56
 * @since 2022.04.0
 */
@Component
public class CustomerHandler {

    private final CustomerRepository repository;

    CustomerHandler(CustomerRepository repository) {
        this.repository = repository;
    }

    Mono<ServerResponse> handlerFindAll(ServerRequest request) {
        var all = this.repository.findAll();
        return ServerResponse.ok().body(all, Customer.class);
    }

    Mono<ServerResponse> handlerFindCustomerById(ServerRequest request) {
        var id = request.pathVariable("id");
        var byId = this.repository.findById(id);
        return ServerResponse.ok().body(byId, Customer.class);
    }

    Mono<ServerResponse> handlerCreateCustomer(ServerRequest request) {
        return request.bodyToMono(Customer.class)
                .flatMap(repository::save)
                .flatMap(saved -> created(URI.create("/fn/customers/" + saved.id())).build());
    }
}
