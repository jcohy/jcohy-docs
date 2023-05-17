package com.jcohy.data.mongodb;

import com.jcohy.docs.reactive_spring.chapter6.mongodb.Customer;
import com.jcohy.docs.reactive_spring.chapter6.mongodb.CustomerRepository;
import com.jcohy.docs.reactive_spring.chapter6.mongodb.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:15:06
 * @since 2022.04.0
 */
@Testcontainers
@DataMongoTest
public class TailableCustomerQueryTest {

    private static final Logger log = LoggerFactory.getLogger(TailableCustomerQueryTest.class);

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.3");

    //
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ReactiveMongoTemplate template;

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    public void before() {
        // <1>
        var capped = CollectionOptions.empty()
                .size(1024 * 1024)
                .maxDocuments(100)
                .capped();

        var recreateCollection = template
                .collectionExists(Order.class)
                .flatMap(exists -> exists ? template.dropCollection(Customer.class) : Mono.just(exists))
                .then(template.createCollection(Customer.class,capped));

        StepVerifier
                .create(recreateCollection)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void tail() throws InterruptedException {

        // <2>
        var people = new ConcurrentLinkedDeque<Customer>();

        // <3>
        StepVerifier
                .create(this.write().then(this.write()))
                .expectNextCount(1)
                .verifyComplete();

        // <4>
        this.repository.findByName("1")
                .doOnNext(people::add)
                .doOnComplete(() -> log.info("complete"))
                .doOnTerminate(() -> log.info("terminated"))
                .subscribe();

        Assertions.assertThat(people).hasSize(2);

        // <5>
        StepVerifier
                .create(this.write().then(this.write()))
                .expectNextCount(1)
                .verifyComplete();

        Thread.sleep(1_000);
        Assertions.assertThat(people).hasSize(4);
    }


    private Mono<Customer> write() {
        return repository.save(new Customer(UUID.randomUUID().toString(),"1"));
    }
}
