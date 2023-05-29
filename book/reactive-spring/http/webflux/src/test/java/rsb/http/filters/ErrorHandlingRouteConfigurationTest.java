package rsb.http.filters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(ErrorHandlingRouteConfiguration.class)
public class ErrorHandlingRouteConfigurationTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void valid() {
		this.client.get().uri("/products/4").exchange().expectStatus().isOk().expectBody().jsonPath(".id")
				.isEqualTo("4");
	}

	@Test
	public void invalid() {
		this.client.get().uri("/products/1").exchange().expectStatus().isNotFound();
	}

}