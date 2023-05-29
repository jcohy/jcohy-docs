package rsb.javareloaded;

import javax.sql.DataSource;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/27:15:12
 * @since 2022.04.0
 */
public class DataSourceCustomerService extends BaseCustomerService {
    public DataSourceCustomerService(DataSource dataSource) {
        super(dataSource);
    }
}
