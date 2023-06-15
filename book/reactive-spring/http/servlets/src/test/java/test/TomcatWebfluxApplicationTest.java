package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:14:58
 * @since 2022.04.0
 */
@WebFluxTest
@Import({GreetingsRouteConfiguration.class, GreetingsController.class})
public class TomcatWebfluxApplicationTest {

    @Autowired
    private WebTestClient client;

    @Test
    void controller() {
        var name = "Kimly";
        doTest("controller",name);
        doTest("functional",name);
    }

    private void doTest(String from, String name) {
        client
                .get()
                .uri("/hello/" + from + "/{name}" , name)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(Greetings.class)
                .value(returnValue -> Assertions.assertEquals(returnValue.message(),
                        "Hello, " + name + " from " + from +  "!"));
    }
}
