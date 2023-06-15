package rsb.bootstrap;

import org.junit.jupiter.api.Assertions;
import org.slf4j.LoggerFactory;

public interface TransactionTestMixin {

	default void testTransactionalityOfSave(CustomerService customerService) {
		var log = LoggerFactory.getLogger(getClass());
		log.info("using customer " + customerService.toString());
		int count = customerService.findAll().size();
		try {
			customerService.save("Bob", null);
		}
		catch (Exception ex) {
			Assertions.assertEquals(count, customerService.findAll().size(),
					"there should be no new records in the database");
			return;
		}
		Assertions.fail();
	}

}
