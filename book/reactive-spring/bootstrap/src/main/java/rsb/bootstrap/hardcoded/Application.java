package rsb.bootstrap.hardcoded;

import rsb.bootstrap.Demo;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/8:10:55
 * @since 2022.04.0
 */
public class Application {

    public static void main(String[] args) {
        var developmentOnlyCustomerService = new DevelopmentOnlyCustomerService();
        Demo.workWithCustomerService(Application.class,developmentOnlyCustomerService);
    }
}
