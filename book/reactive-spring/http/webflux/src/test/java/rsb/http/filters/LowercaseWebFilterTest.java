package rsb.http.filters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest({ LowercaseWebConfiguration.class, LowercaseWebFilter.class })
public class LowercaseWebFilterTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void greet() throws Exception {
		test("/hi/jane", "jane");
		test("/HI/jane", "jane");
	}

	private void test(String path, String match) {

		this.client //
				.get() //
				.uri("http://localhost:8080/" + path) //
				.exchange().expectStatus().isOk() //
				.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN) //
				.expectBody(String.class)
				.value(message -> message.equalsIgnoreCase(String.format("Hello, %s!", match)));

	}

}