package rsb.testing.producer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:15:35
 * @since 2022.04.0
 */
@WebFluxTest // <1>
@Import(CustomerWebConfiguration.class) // <2>
public class CustomerWebTest {

    @Autowired
    private WebTestClient client; //<3>

    @MockBean
    private CustomerRepository repository; // <4>

    @Test
    public void getAll() {

        // <5>
        Mockito.when(this.repository.findAll()).thenReturn(Flux.just(new Customer("1","A"),new Customer("2","B")));

        // <6>
        this.client.get()
                .uri("/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo("1")
                .jsonPath("$.[0].name").isEqualTo("A")
                .jsonPath("$.[1].id").isEqualTo("2")
                .jsonPath("$.[1].name").isEqualTo("B");
    }
}
