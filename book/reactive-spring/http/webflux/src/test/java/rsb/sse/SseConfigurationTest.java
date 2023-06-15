package rsb.sse;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@Slf4j
@WebFluxTest({ SseConfiguration.class })
public class SseConfigurationTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void sse() {

		StepVerifier //
				.create( //
						this.client //
								.get() //
								.uri("/sse/2") //
								.exchange() //
								.expectStatus().isOk() //
								.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM) //
								.returnResult(String.class) //
								.getResponseBody()//
				) //
				.expectNext("# 1") //
				.expectNext("# 2") //
				.verifyComplete();

	}

}