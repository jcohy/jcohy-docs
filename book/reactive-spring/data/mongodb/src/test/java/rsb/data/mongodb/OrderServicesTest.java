package rsb.data.mongodb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
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
 * @version 2022.04.0 2023/5/17:14:48
 * @since 2022.04.0
 */
@Testcontainers
@DataMongoTest // <1>
@Import({TransactionConfiguration.class, OrderService.class})
public class OrderServicesTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.3");

    // <2>
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService service;

    @Autowired
    private ReactiveMongoTemplate template;

    @BeforeEach // <3>
    public void configureCollectionBeforeTests() {
        var createIfMissing = template.collectionExists(Order.class)
                .filter(x -> !x)
                .flatMap(exists -> template.createCollection(Order.class))
                .thenReturn(true);

        StepVerifier
                .create(createIfMissing)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test // <4>
    public void createOrders() {
        var orders = this.repository
                .deleteAll()
                .thenMany(this.service.createOrders("1","2","3"))
                .thenMany(this.repository.findAll());

        StepVerifier
                .create(orders)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test // <5>
    public void transactionalOperatorRollback() {
        this.runTransactionalTest(this.service.createOrders("1","2",null));
    }

    private void runTransactionalTest(Flux<Order> ordersInTx) {
        var orders = this.repository
                .deleteAll()
                .thenMany(ordersInTx)
                .thenMany(this.repository.findAll());

        StepVerifier
                .create(orders)
                .expectNextCount(0)
                .verifyError();

        StepVerifier
                .create(this.repository.findAll())
                .expectNextCount(0)
                .verifyComplete();

    }
}
