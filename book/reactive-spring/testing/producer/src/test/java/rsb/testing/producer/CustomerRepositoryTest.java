package rsb.testing.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:15:18
 * @since 2022.04.0
 */
@Testcontainers
@DataMongoTest // <1>
public class CustomerRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.3");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    // <2>
    @Autowired
    private CustomerRepository repository;

    // <3>
    @Test
    public void findByName() {
        var commonName = "Jane";
        var one = new Customer("1",commonName);
        var two = new Customer("2","John");
        var three = new Customer("3",commonName);

        var setupPublisher = this.repository
                .deleteAll()
                .thenMany(this.repository.saveAll(Flux.just(one,two,three)))
                .thenMany(this.repository.findByName(commonName));

        var customerPredicate = (Predicate<Customer>) customer -> commonName.equalsIgnoreCase(customer.name()); // <4>

        // <5>
        StepVerifier
                .create(setupPublisher)
                .expectNextMatches(customerPredicate)
                .expectNextMatches(customerPredicate)
                .verifyComplete();
    }
}
