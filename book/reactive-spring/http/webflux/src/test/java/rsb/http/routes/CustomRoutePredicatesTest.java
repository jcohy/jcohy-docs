package rsb.http.routes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.equalToIgnoringCase;

@WebFluxTest({ CustomRoutePredicates.class })
public class CustomRoutePredicatesTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void caseInsensitiveRequestMatching() {
		var uppercaseUri = "/GREETINGS/World";
		var lowercaseUri = "/greetings/World";
		var expectedString = "Hello, World!";

		this.webTestClient.get().uri(uppercaseUri).exchange().expectBody(String.class)
				.value(equalToIgnoringCase(expectedString));

		this.webTestClient.get().uri(lowercaseUri).exchange().expectBody(String.class)
				.value(equalToIgnoringCase(expectedString));

	}

}