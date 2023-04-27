package com.jcohy.docs.reactive_spring.chapter3;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/27:12:54
 * @since 2022.04.0
 */
public class DataSourceUtils {

    public static DataSource initializeDdl(DataSource dataSource) { // <1>
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("/schema.sql")); // <2>
        DatabasePopulatorUtils.execute(populator,dataSource);
        return dataSource;
    }
}
