package rsb.bootstrap.templates;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.support.TransactionTemplate;
import rsb.bootstrap.BaseClass;
import rsb.bootstrap.CustomerService;
import rsb.bootstrap.DataSourceUtils;
import rsb.bootstrap.TransactionTestMixin;

public class TransactionTemplateCustomerServiceTest extends BaseClass implements TransactionTestMixin {

	private final TransactionTemplateCustomerService customerService;

	public TransactionTemplateCustomerServiceTest() {
		var ds = DataSourceUtils.initializeDdl(new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build());
		var txm = new DataSourceTransactionManager(ds);
		var tt = new TransactionTemplate(txm);
		this.customerService = new TransactionTemplateCustomerService(ds, tt);
	}

	@Override
	@Test
	public void insert() {
		super.insert();
		this.testTransactionalityOfSave(getCustomerService());
	}

	@Override
	public CustomerService getCustomerService() {
		return this.customerService;
	}

}