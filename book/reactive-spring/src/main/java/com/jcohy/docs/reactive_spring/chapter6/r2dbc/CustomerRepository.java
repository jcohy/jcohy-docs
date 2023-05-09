package com.jcohy.docs.reactive_spring.chapter6.r2dbc;

import com.jcohy.docs.reactive_spring.chapter6.common.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/9:16:04
 * @since 2022.04.0
 */
// <1>
public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {

    // <2>
    @Query("select id, email from customer c where c.email = $1")
    Flux<Customer> findByEmail(String email);
}
