package com.jcohy.data;

import com.jcohy.docs.reactive_spring.chapter6.common.Customer;
import com.jcohy.docs.reactive_spring.chapter6.common.SimpleCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Locale;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/9:15:07
 * @since 2022.04.0
 */
@Testcontainers
public abstract class BaseCustomerRepositoryTest {

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.sql.init.mode",() -> "always");
        registry.add("spring.r2dbc.url",()  -> "r2dbc:tc:postgresql://rsbhost/rsb?TC_IMAGE_TAG=9.6.8");
    }

    // <1>
    public abstract SimpleCustomerRepository getRepository();

    // <2>
//    @Autowired
//    private CustomerDatabaseInitializer initializer;

    @Test
    public void delete() {
        var repository = getRepository();

        var data = repository.findAll()
                .flatMap(c -> repository.deleteById(c.id()))
                .thenMany(Flux.just(
                        new Customer(null,"first@email.com"),
                        new Customer(null,"second@email.com"),
                        new Customer(null,"third@email.com")))
                .flatMap(repository::save);

        StepVerifier.create(data)
                .expectNextCount(3)
                .verifyComplete();

        StepVerifier.create(repository.findAll().take(1).flatMap(customer -> repository.deleteById(customer.id())).then())
                        .verifyComplete();

        StepVerifier.create(repository.findAll())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void saveAndFindAll() {
        var repository = getRepository();

        var data = Flux.just(
                new Customer(null,"first@email.com"),
                new Customer(null,"second@email.com"),
                new Customer(null,"third@email.com"))
                .flatMap(repository::save);
        StepVerifier.create(data)
                .expectNextCount(2)
                .expectNextMatches(customer -> customer.id() != null && customer.email() != null)
                .verifyComplete();
    }

    @Test
    public void findById() {
        var repository = getRepository();

        var insert = Flux.just(
                        new Customer(null,"first@email.com"),
                        new Customer(null,"second@email.com"),
                        new Customer(null,"third@email.com"))
                .flatMap(repository::save);

        var all = repository.findAll()
                .flatMap(customer -> repository.deleteById(customer.id()))
                .thenMany(insert.thenMany(repository.findAll()));

        StepVerifier.create(all)
                .expectNextCount(3)
                .verifyComplete();

        var recordsById = repository.findAll()
                .flatMap(customer -> Mono.zip(Mono.just(customer),repository.findById(customer.id())))
                .filterWhen(tuple2 ->Mono.just(tuple2.getT1().equals(tuple2.getT2())));

        StepVerifier.create(recordsById)
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    public void update() {
        var repository = getRepository();

        var email = "test@email.com";

        StepVerifier
                .create(repository.findAll()
                        .flatMap(customer -> repository.deleteById(customer.id()))
                        .thenMany(repository.save(new Customer(null,email.toUpperCase(Locale.ROOT)))))
                .expectNextMatches(p -> p.id() != null)
                .verifyComplete();

        StepVerifier
                .create(repository.findAll())
                .expectNextCount(3)
                .verifyComplete();

        StepVerifier
                .create(repository.findAll()
                        .map(customer -> new Customer(customer.id(), customer.email().toUpperCase(Locale.ROOT)))
                        .flatMap(repository::update))
                .expectNextMatches(customer -> customer.email().equals(email.toUpperCase(Locale.ROOT)))
                .verifyComplete();
    }
}
