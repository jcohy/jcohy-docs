package rsb.bootstrap.templates;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import rsb.bootstrap.CustomerService;
import rsb.bootstrap.DataSourceUtils;
import rsb.bootstrap.Demo;

import javax.sql.DataSource;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/27:16:22
 * @since 2022.04.0
 */
public class Application {

    public static void main(String[] args) {
        var dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();

        var initializeDataSource = DataSourceUtils.initializeDdl(dataSource); // <1>
        var dsTxManager = new DataSourceTransactionManager(initializeDataSource); // <2>
        var transactionTemplate = new TransactionTemplate(dsTxManager); // <3>
        // <4>
        var customerService = new TransactionTemplateCustomerService(initializeDataSource,transactionTemplate);
        Demo.workWithCustomerService(Application.class,customerService);
    }
}
