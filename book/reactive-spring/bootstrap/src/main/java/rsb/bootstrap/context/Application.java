package rsb.bootstrap.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import rsb.bootstrap.CustomerService;
import rsb.bootstrap.DataSourceConfiguration;
import rsb.bootstrap.Demo;
import rsb.bootstrap.SpringUtils;
import rsb.bootstrap.templates.TransactionTemplateCustomerService;

import javax.sql.DataSource;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/8:10:49
 * @since 2022.04.0
 */
// <1>
@Configuration
@Import(DataSourceConfiguration.class) // <2>
public class Application {

    // <3>
    @Bean
    PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    TransactionTemplateCustomerService customerService(DataSource ds, TransactionTemplate tt) {
        return new TransactionTemplateCustomerService(ds,tt);
    }

    @Bean
    TransactionTemplate transactionTemplate(PlatformTransactionManager tm) {
        return new TransactionTemplate(tm);
    }

    public static void main(String[] args) {
        // <4>
        var applicationContext = SpringUtils.run(Application.class,"prod");

        // <5>
        var customerService = applicationContext.getBean(CustomerService.class);
        Demo.workWithCustomerService(Application.class,customerService);
    }
}
