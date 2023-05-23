package com.jcohy.docs.reactive_spring.chapter7.webflux.http.customers;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:10:29
 * @since 2022.04.0
 */
@Repository
public class CustomerRepository {

    private final Map<String,Customer> data = new ConcurrentHashMap<>();

    Mono<Customer> findById(String id) {
        return Mono.just(this.data.get(id));
    }

    Mono<Customer> save(Customer customer) {
        var uuid = UUID.randomUUID().toString();
        this.data.put(uuid,new Customer(uuid, customer.name()));
        return Mono.just(this.data.get(uuid));
    }

    Flux<Customer> findAll() {
        return Flux.fromIterable(this.data.values());
    }
}
