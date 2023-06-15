package rsb.bootstrap.hardcoded;

import rsb.bootstrap.BaseClass;
import rsb.bootstrap.CustomerService;

public class HardcodedTest extends BaseClass {

	private final CustomerService customerService;

	public HardcodedTest() {
		this.customerService = new DevelopmentOnlyCustomerService();
	}

	@Override
	public CustomerService getCustomerService() {
		return this.customerService;
	}

}
