package rsb.javareloaded;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/27:13:05
 * @since 2022.04.0
 */
public class Application {

    public static void main(String[] args) {
//        DevelopmentOnlyCustomerService customerService = new DevelopmentOnlyCustomerService();
//        Demo.workWithCustomerService(Application.class,customerService);

        // <1>
        EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        // <2>
        DataSource initializeDataSource = DataSourceUtils.initializeDdl(dataSource);
        CustomerService customerService = new DataSourceCustomerService(initializeDataSource);
        Demo.workWithCustomerService(Application.class,customerService);
    }
}
