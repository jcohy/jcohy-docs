package rsb.http.routes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest({ SimpleFunctionalEndpointConfiguration.class, GreetingsHandlerFunction.class })
public class SimpleFunctionalEndpointConfigurationTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void sup() {
		this.doTest("/sup", "Hodor!");
	}

	@Test
	public void hello() {
		this.doTest("/hello/world", "Hello world!");
	}

	@Test
	public void hodor() {
		this.doTest("/hodor", "Hodor!");
	}

	private void doTest(String path, String result) {
		this.webTestClient //
				.get() //
				.uri(path) //
				.exchange() //
				.expectBody(String.class)//
				.value(str -> Assertions.assertEquals(result, str));
	}

}