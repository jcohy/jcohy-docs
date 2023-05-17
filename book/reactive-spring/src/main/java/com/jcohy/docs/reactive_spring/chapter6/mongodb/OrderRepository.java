package com.jcohy.docs.reactive_spring.chapter6.mongodb;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:14:21
 * @since 2022.04.0
 */
public interface OrderRepository extends ReactiveCrudRepository<Order,String> {

    Flux<Order> findByProductId(String productId);

}
