package rsb.bootstrap.enable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import rsb.bootstrap.CustomerService;
import rsb.bootstrap.DataSourceConfiguration;
import rsb.bootstrap.Demo;
import rsb.bootstrap.SpringUtils;

import javax.sql.DataSource;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/28:10:40
 * @since 2022.04.0
 */
@Configuration
@EnableTransactionManagement // <1>
@ComponentScan
@Import(DataSourceConfiguration.class)
public class Application {


    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    TransactionTemplate transactionTemplate(PlatformTransactionManager tm) {
        return new TransactionTemplate(tm);
    }

    public static void main(String[] args) {
        var applicationContext = SpringUtils.run(rsb.bootstrap.basicdi.Application.class,"prod");

        var customerService = applicationContext.getBean(CustomerService.class);
        Demo.workWithCustomerService(Application.class,customerService);
    }
}
