package rsb.data.mongodb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/17:14:25
 * @since 2022.04.0
 */
@Testcontainers
@DataMongoTest
class OrderRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.3");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private OrderRepository orderRepository;

    private final Collection<Order> orders = List.of(
            new Order(UUID.randomUUID().toString(),"1"),
            new Order(UUID.randomUUID().toString(),"2"),
            new Order(UUID.randomUUID().toString(),"2")
    );

    private final Predicate<Order> predicate = order ->
      this.orders
              .stream()
              .filter(candidateOrder -> candidateOrder.id().equalsIgnoreCase(order.id()))
              .anyMatch(candidateOrder -> candidateOrder.productId().equalsIgnoreCase(order.productId()));


    @BeforeEach
    public void before() {
        var saveAll = this.orderRepository
                .deleteAll()
                .thenMany(this.orderRepository.saveAll(this.orders));

        StepVerifier // <1>
                .create(saveAll)
                .expectNextMatches(this.predicate)
                .expectNextMatches(this.predicate)
                .expectNextMatches(this.predicate)
                .verifyComplete();
    }

    @Test
    public void findAll() {
        StepVerifier // <2>
                .create(this.orderRepository.findAll())
                .expectNextMatches(this.predicate)
                .expectNextMatches(this.predicate)
                .expectNextMatches(this.predicate)
                .verifyComplete();
    }

    @Test
    public void findByProductId() {
        StepVerifier // <3>
                .create(this.orderRepository.findByProductId("2"))
                .expectNextCount(2)
                .verifyComplete();
    }
}