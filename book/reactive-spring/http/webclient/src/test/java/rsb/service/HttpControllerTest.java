package rsb.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import rsb.client.ClientProperties;
import rsb.client.DefaultClient;
import rsb.client.DefaultConfiguration;
import rsb.client.Greeting;

@SpringBootTest(classes = { DefaultConfiguration.class, ClientProperties.class, HttpServiceApplication.class }, //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		properties = { "spring.profiles.active=client", "server.port=8080",
				"spring.main.web-application-type=reactive" })
public class HttpControllerTest {

	@Autowired
	private DefaultClient defaultClient;

	@Test
	public void greetSingle() {
		Mono<Greeting> helloMono = this.defaultClient.getSingle("Madhura");
		StepVerifier.create(helloMono).expectNextMatches(g -> g.message().contains("Hello Madhura"))
				.verifyComplete();
	}

	@Test
	public void greetMany() {
		Flux<Greeting> helloFlux = this.defaultClient.getMany("Stephane").take(2);
		String msg = "Hello Stephane";
		StepVerifier.create(helloFlux).expectNextMatches(g -> g.message().contains(msg))
				.expectNextMatches(g -> g.message().contains(msg)).verifyComplete();
	}

}