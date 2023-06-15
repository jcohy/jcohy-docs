package rsb.http.customers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractRestBaseClass {

	abstract String rootUrl();

	@Autowired
	private WebTestClient client;

	@MockBean
	private CustomerRepository customerRepository;

	private final Collection<Customer> results = List.of(new Customer("1", "A"), new Customer("2", "B"), //
			new Customer("3", "C"), new Customer("4", "D"));

	private final AtomicReference<Customer> saved = new AtomicReference<>();

	@BeforeEach
	public void before() {

		var iterable = Flux.fromIterable(this.results);

		Mockito //
				.when(this.customerRepository.findAll()) //
				.thenReturn(iterable);

		Mockito//
				.when(this.customerRepository.findById("1"))//
				.thenAnswer(invocation -> Mono.just(new Customer("1", "A")));

		Mockito //
				.when(this.customerRepository.save(Mockito.any()))//
				.then(invocation -> {
					Customer customer = (Customer) invocation.getArguments()[0];
					String uid = UUID.randomUUID().toString();
					this.saved.set(new Customer(uid, customer.name()));
					return Mono.just(this.saved.get());
				});
	}

	@Test
	public void all() throws Exception {

		var customerFluxExchangeResult = this.client.get() //
				.uri(rootUrl() + "/customers") //
				.exchange() //
				.expectStatus().isOk() //
				.expectHeader() //
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON) //
				.returnResult(Customer.class);

		var responseBody = customerFluxExchangeResult.getResponseBody();

		StepVerifier //
				.create(responseBody) //
				.expectNextCount(results.size()).verifyComplete();
	}

	@Test
	public void byId() {

		var getCustomerByIdResult = this.client.get() //
				.uri(rootUrl() + "/customers/1") //
				.exchange() //
				.expectStatus().isOk() //
				.expectHeader() //
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON) //
				.returnResult(Customer.class);

		var responseBody = getCustomerByIdResult.getResponseBody();

		StepVerifier //
				.create(responseBody) //
				.expectNextMatches(customer -> customer.name().equalsIgnoreCase("A")).verifyComplete();
	}

	@Test
	public void create() throws Exception {
		var krusty = "Krusty";
		this.client.post() //
				.uri(rootUrl() + "/customers") //
				.contentType(MediaType.APPLICATION_JSON) //
				.body(BodyInserters.fromValue(new Customer(UUID.randomUUID().toString(), krusty))) //
				.exchange() //
				.expectHeader().exists(HttpHeaders.LOCATION) //
				.expectStatus().isCreated();
		Assertions.assertTrue(this.saved.get().name().equalsIgnoreCase(krusty));
	}

}
