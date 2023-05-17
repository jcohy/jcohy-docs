package com.jcohy.data.testing;

import com.jcohy.docs.reactive_spring.chapter6.common.Customer;
import com.jcohy.docs.reactive_spring.chapter6.common.CustomerService;
import com.jcohy.docs.reactive_spring.chapter6.common.SimpleCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:15:55
 * @since 2022.04.0
 */
@Testcontainers
abstract public class BaseCustomerServiceTest {

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.sql.init.mode",() -> "always");
        registry.add("spring.r2dbc.url",() -> "r2bdc:tc:postgresql://rsbhost/rsb?TV_IMAGE_TAG=9.6.8");
    }

    abstract public SimpleCustomerRepository getCustomerRepository();

    private SimpleCustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void reset() {
        this.customerRepository = getCustomerRepository();
    }

    @Test
    public void badUpset() {
        StepVerifier
                .create(this.customerRepository
                        .findAll()
                        .flatMap(c -> this.customerRepository.deleteById(c.id())))
                .verifyComplete();

        var badEmail = "bad";

        var firstWrite = this.customerService
                .upsert(badEmail)
                .thenMany(this.customerRepository.findAll());

        StepVerifier
                .create(firstWrite)
                .expectError()
                .verify();

    }

    @Test
    public void goodUpset() {
        StepVerifier
                .create(this.customerRepository
                        .findAll()
                        .flatMap(c -> this.customerRepository.deleteById(c.id())))
                .verifyComplete();

        var validEmail = "a@b.com";

        var firstWrite = this.customerService
                .upsert(validEmail)
                .thenMany(this.customerRepository.findAll());

        StepVerifier
                .create(firstWrite)
                .expectNextCount(1)
                .verifyComplete();

        var secondWrite = this.customerService
                .upsert(validEmail)
                .thenMany(this.customerRepository.findAll());

        StepVerifier
                .create(secondWrite)
                .expectNextCount(1)
                .verifyComplete();

    }


    @Test
    public void resetDatabase() {
        var resetAndFind = this.customerRepository
                .save(new Customer(null,"a@b.com"))
                .thenMany(this.customerService.resetDatabase())
                .thenMany(this.customerRepository.findAll());

        StepVerifier
                .create(resetAndFind)
                .expectNextCount(0)
                .verifyComplete();
    }


    @Test
    public void normalizeEmail()  {
        var email = "a@b.com";

        StepVerifier
                .create(customerRepository.save(new Customer(null,email)))
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier
                .create(customerRepository.findAll())
                .expectNextCount(1)
                .verifyComplete();

        Flux<Customer> customerFlux = customerService.normalizeEmails();

        StepVerifier
                .create(customerFlux)
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier
                .create(customerRepository.findAll())
                .expectNextMatches(c -> c.email().toUpperCase().equals(email.toUpperCase()))
                .verifyComplete();
    }
}
