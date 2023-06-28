package rsb.http.routes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@WebFluxTest({ NestedFunctionalEndpointConfiguration.class, NestedHandler.class })
public class NestedFunctionalEndpointConfigurationTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void nestedDefaultJson() {
		this.client.get() //
				.uri("/nested").accept(MediaType.APPLICATION_JSON) //
				.exchange() //
				.expectStatus().isOk() //
				.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) //
				.expectBody() //
				.jsonPath("@.message").isEqualTo("Hello world!");
	}

	@Test
	public void nestedNameJson() throws Exception {
		this.client.get() //
				.uri("/nested/jane").accept(MediaType.APPLICATION_JSON).exchange() //
				.expectStatus().isOk() //
				.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON).expectBody() //
				.jsonPath("@.message").isEqualTo("Hello jane!");
	}

	@Test
	public void nestedSse() throws Exception {

		StepVerifier //
				.create(go()) //
				.consumeNextWith(str -> assertThat(str, endsWith("1"))) //
				.consumeNextWith(str -> assertThat(str, endsWith("2"))) //
				.thenCancel() //
				.verify();
	}

	Flux<String> go() {
		return this.client //
				.get() //
				.uri("/nested").accept(TEXT_EVENT_STREAM)//
				.exchange() //
				.expectStatus().isOk() //
				.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM) //
				.returnResult(String.class) //
				.getResponseBody();
	}

}