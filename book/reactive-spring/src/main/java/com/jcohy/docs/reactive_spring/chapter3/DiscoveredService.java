package com.jcohy.docs.reactive_spring.chapter3;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/28:15:35
 * @since 2022.04.0
 */
@Service // <1>
public class DiscoveredService extends TransactionTemplateCustomerService {

    // <2>
    public DiscoveredService(DataSource dataSource, TransactionTemplate transactionTemplate) {
        super(dataSource, transactionTemplate);
    }
}
