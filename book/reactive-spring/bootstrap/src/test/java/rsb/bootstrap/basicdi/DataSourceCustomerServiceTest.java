package rsb.bootstrap.basicdi;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import rsb.bootstrap.BaseClass;
import rsb.bootstrap.CustomerService;
import rsb.bootstrap.DataSourceUtils;

public class DataSourceCustomerServiceTest extends BaseClass {

	private final DataSourceCustomerService customerService;

	public DataSourceCustomerServiceTest() {
		var dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
		this.customerService = new DataSourceCustomerService(DataSourceUtils.initializeDdl(dataSource));
	}

	@Override
	public CustomerService getCustomerService() {
		return this.customerService;
	}

}