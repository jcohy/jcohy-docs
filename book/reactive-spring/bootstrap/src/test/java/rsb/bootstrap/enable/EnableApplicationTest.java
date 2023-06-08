package rsb.bootstrap.enable;

import org.junit.jupiter.api.Test;
import rsb.bootstrap.ApplicationContextAwareBaseClass;
import rsb.bootstrap.TransactionTestMixin;

public class EnableApplicationTest extends ApplicationContextAwareBaseClass implements TransactionTestMixin {

	@Override
	@Test
	public void insert() {
		super.insert();

		this.testTransactionalityOfSave(getCustomerService());
	}

	@Override
	protected Class<?> getConfigurationClass() {
		return Application.class;
	}

}