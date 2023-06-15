package rsb.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

@SpringBootTest(classes = { ServiceApplication.class }, //
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, //
		properties = { "spring.profiles.active=client", "spring.main.web-application-type=reactive" })
public class GreetingControllerTest {

	@LocalServerPort
	int port;

	@Test
	public void greetAuthenticated() {
		var user = "jlong";
		var pw = "pw";
		var exchangeFilterFunction = ExchangeFilterFunctions.basicAuthentication(user, pw);
		var webClient = WebClient.builder().filter(exchangeFilterFunction).build();
		var mapMono = webClient.get().uri("http://localhost:" + port + "/greetings").retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
				});
		StepVerifier.create(mapMono).expectNextMatches(mp -> mp.get("greetings").contains("jlong")).verifyComplete();
	}

}
