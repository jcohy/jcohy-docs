package com.jcohy.docs.reactive_spring.chapter6.springdata;

import com.jcohy.docs.reactive_spring.chapter6.common.Customer;
import io.r2dbc.spi.Parameter;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:16:14
 * @since 2022.04.0
 */
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    @Query("select id, email from customer  c where c.email = $1")
    Flux<Customer> findByEmail(String email);
}
