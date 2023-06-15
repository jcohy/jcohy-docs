package rsb.testing.consumer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:16:20
 * @since 2022.04.0
 */
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = ConsumerApplication.class)
@AutoConfigureStubRunner(
        ids = StubRunnerCustomerClientTest.PRODUCER_ARTIFACT_ID,// <1>
        stubsMode = StubRunnerProperties.StubsMode.LOCAL // <2>
)
public class StubRunnerCustomerClientTest {

    private static final Logger log = LoggerFactory.getLogger(StubRunnerCustomerClientTest.class);

    final static String PRODUCER_ARTIFACT_ID = "rsb:producer";

    @Autowired
    private CustomerClient client;

    @StubRunnerPort(StubRunnerCustomerClientTest.PRODUCER_ARTIFACT_ID)
    private int portOfProducerService; // <3>

    @Test
    public void getAllCustomers() {
        var base = "localhost:" + this.portOfProducerService;

        this.client.setBase(base);
        log.info("setBase( " + base + ")");

        Flux<Customer> customers = this.client.getAllCustomer();

        StepVerifier.create(customers)
                .expectNext(new Customer("1","Jane"))
                .expectNext(new Customer("2","John"))
                .verifyComplete();
    }
}
