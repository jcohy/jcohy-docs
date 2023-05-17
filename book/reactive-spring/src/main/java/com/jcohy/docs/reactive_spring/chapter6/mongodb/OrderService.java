package com.jcohy.docs.reactive_spring.chapter6.mongodb;

import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:14:42
 * @since 2022.04.0
 */
@Service
public class OrderService {

    private final ReactiveMongoTemplate template;

    private final TransactionalOperator operator;

    public OrderService(ReactiveMongoTemplate template, TransactionalOperator operator) {
        this.template = template;
        this.operator = operator;
    }

    // <1>
    public Flux<Order> createOrders(String... productIds) {
        return this.operator.execute(status -> buildOrderFlux(template::insert,productIds));
    }

    private Flux<Order> buildOrderFlux(Function<Order, Mono<Order>> callback, String[] productIds) {
        return Flux
                .just(productIds)
                .map(pid -> {
                    Assert.notNull(pid,"the product ID should't be null");
                    return pid;
                })
                .map( x -> new Order(null,x))
                .flatMap(callback);
    }
}
