package rsb.bootstrap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public abstract class BaseClass {

	public abstract CustomerService getCustomerService();

	@Test
	public void insert() {
		var bob = getCustomerService().save("Bob");
		Assertions.assertNotNull(bob);
		Assertions.assertEquals(bob.size(), 1);
	}

	@Test
	public void getAllCustomers() {
		Stream.of("A", "B").forEach(getCustomerService()::save);
		Assertions.assertTrue(getCustomerService().findAll().size() > 2);
	}

	@Test
	public void byId() {
		var id = getCustomerService().save("A").iterator().next().id();
		Assertions.assertEquals(id, getCustomerService().findById(id).id());
	}

}
