package com.jcohy.docs.reactive_spring.chapter3;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/28:11:49
 * @since 2022.04.0
 */
@Configuration
public class DataSourceConfiguration {

    // <1>
    @Configuration
    @Profile("prod") // <2>
    @PropertySource("application-prod.properties") // <3>
    public static class ProductionConfiguration{
        @Bean
        DataSource productionDataSource(@Value("${spring.datasource.url}") String url, // <4>
                                        @Value("${spring.datasource.username}") String username,
                                        @Value("${spring.datasource.password}") String password,
                                        @Value("${spring.datasource.driver-class-name}")Class<Driver> driverClass) { // <5>
            DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
            dataSource.setDriverClassName(driverClass.getName());
            return dataSource;
        }
    }

    @Configuration
    @Profile("default") // <6>
    @PropertySource("application-default.properties")
    public static class DevelopmentConfiguration {

        @Bean
        DataSource developmentDataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }
    }

    @Bean
    DataSourcePostProcessor dataSourcePostProcessor() {
        return new DataSourcePostProcessor();
    }

    // <7>
    private static class DataSourcePostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

            if(bean instanceof DataSource) {
                DataSourceUtils.initializeDdl(DataSource.class.cast(bean));
            }
            return bean;
        }
    }

}
