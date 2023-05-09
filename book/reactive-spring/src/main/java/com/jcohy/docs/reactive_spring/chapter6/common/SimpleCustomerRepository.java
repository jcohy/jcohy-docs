package com.jcohy.docs.reactive_spring.chapter6.common;

import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/9:14:59
 * @since 2022.04.0
 */
@NoRepositoryBean
public interface SimpleCustomerRepository {

    Mono<Customer> save(Customer c);

    Flux<Customer> findAll();

    Mono<Customer> update(Customer c);

    Mono<Customer> findById(Integer id);

    Mono<Void> deleteById(Integer id);
}
