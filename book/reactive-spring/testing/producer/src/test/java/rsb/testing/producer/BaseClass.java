package rsb.testing.producer;

import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:16:11
 * @since 2022.04.0
 */
// <1>
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "server.port=0")
public class BaseClass {

    private static final Logger log = LoggerFactory.getLogger(BaseClass.class);

    // <3>
    @LocalServerPort
    private int port;

    // <4>
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private RouterFunction<?> routerFunction;

    @BeforeEach
    public void before() {
        log.info("the embedded test web server is available on port" + this.port);

        // <5>
        Mockito.when(this.customerRepository.findAll())
                .thenReturn(Flux.just(new Customer("1","Jane"),new Customer("2","John")));

        // <6>
        RestAssuredWebTestClient.standaloneSetup(this.routerFunction);
    }

    @Configuration
    @Import(ProducerApplication.class)
    public static class TestConfiguration {}
}
