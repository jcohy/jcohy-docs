package rsb.bootstrap.bootiful;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rsb.bootstrap.BaseClass;
import rsb.bootstrap.CustomerService;
import rsb.bootstrap.TransactionTestMixin;

@SpringBootTest
public class BootifulCustomerServiceTest extends BaseClass implements TransactionTestMixin {

	@Autowired
	private CustomerService customerService;

	@Override
	public CustomerService getCustomerService() {
		return this.customerService;
	}

	@Override
	@Test
	public void insert() {
		super.insert();
		this.testTransactionalityOfSave(getCustomerService());
	}

}
