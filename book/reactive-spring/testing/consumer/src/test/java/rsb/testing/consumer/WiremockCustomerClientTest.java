package rsb.testing.consumer;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import reactor.test.StepVerifier;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:15:15
 * @since 2022.04.0
 */
@Import(ConsumerApplication.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT) // <1>
@AutoConfigureWireMock(port = 0)  // <2>
public class WiremockCustomerClientTest {

    // <3>
    @Autowired
    private CustomerClient client;

    @Autowired
    private Environment environment;

    @BeforeEach
    public void setupWireMock() {
        var wiremockServerPort = this.environment.getProperty("wiremock.server.port",Integer.class);

        var base = String.format("%s:%s","localhost",wiremockServerPort);
        this.client.setBase(base);

        var json = """
                {
                    {"id":"1","name":"Jane"},
                    {"id":"2","name":"John"}
                }
                """;

        // <4>
        WireMock.stubFor(
                WireMock.get("/customers")
                        .willReturn(WireMock.aResponse()
                                .withHeader(CONTENT_TYPE,APPLICATION_JSON_VALUE)
                                .withBody(json)));
    }

    @Test
    public void getAllCustomers() {
        var customers = this.client.getAllCustomer();
        StepVerifier.create(customers)
                .expectNext(new Customer("1","Jane"))
                .expectNext(new Customer("2","John"))
                .verifyComplete();
    }
}
