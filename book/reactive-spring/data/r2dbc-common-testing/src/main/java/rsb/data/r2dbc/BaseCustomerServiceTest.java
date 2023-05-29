package rsb.data.r2dbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Testcontainers
abstract public class BaseCustomerServiceTest {

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.sql.init.mode", () -> "always");
		registry.add("spring.r2dbc.url", () -> "r2dbc:tc:postgresql://rsbhost/rsb?TC_IMAGE_TAG=9.6.8");
	}

	abstract public SimpleCustomerRepository getCustomerRepository();

	private SimpleCustomerRepository customerRepository;

	@Autowired
	private CustomerService customerService;

	@BeforeEach
	public void reset() {
		this.customerRepository = getCustomerRepository();
	}

	@Test
	public void badUpsert() throws Exception {
		StepVerifier.create(this.customerRepository.findAll().flatMap(c -> this.customerRepository.deleteById(c.id())))
				.verifyComplete();
		var badEmail = "bad";
		var firstWrite = this.customerService.upsert(badEmail).thenMany(this.customerRepository.findAll());
		StepVerifier.create(firstWrite).expectError().verify();
		StepVerifier.create(this.customerRepository.findAll()).expectNextCount(0).verifyComplete();
	}

	@Test
	public void goodUpsert() throws Exception {
		StepVerifier.create(this.customerRepository.findAll().flatMap(c -> this.customerRepository.deleteById(c.id())))
				.verifyComplete();
		var validEmail = "a@b.com";
		var firstWrite = this.customerService.upsert(validEmail).thenMany(this.customerRepository.findAll());
		StepVerifier.create(firstWrite).expectNextCount(1).verifyComplete();
		var secondWrite = this.customerService.upsert(validEmail).thenMany(this.customerRepository.findAll());
		StepVerifier.create(secondWrite).expectNextCount(1).verifyComplete();
	}

	@Test
	public void resetDatabase() {
		var resetAndFind = this.customerRepository//
				.save(new Customer(null, "a@b.com")) //
				.thenMany(this.customerService.resetDatabase())//
				.thenMany(this.customerRepository.findAll()); //
		StepVerifier.create(resetAndFind).expectNextCount(0).verifyComplete();

	}

	@Test
	public void normalizeEmails() throws Exception {

		var email = "a@b.com";
		StepVerifier.create(customerRepository.save(new Customer(null, email))).expectNextCount(1).verifyComplete();
		StepVerifier.create(customerRepository.findAll()).expectNextCount(1).verifyComplete();
		Flux<Customer> customerFlux = customerService.normalizeEmails();
		StepVerifier.create(customerFlux).expectNextCount(1).verifyComplete();

		StepVerifier.create(customerRepository.findAll())
				.expectNextMatches(c -> c.email().toUpperCase().equals(email.toUpperCase())).verifyComplete();
	}

}
