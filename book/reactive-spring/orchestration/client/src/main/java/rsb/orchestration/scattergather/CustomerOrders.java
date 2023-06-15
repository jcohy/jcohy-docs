package rsb.orchestration.scattergather;

import rsb.orchestration.Customer;
import rsb.orchestration.Order;
import rsb.orchestration.Profile;

import java.util.Collection;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/7:15:38
 * @since 2022.04.0
 */
public record CustomerOrders(Customer customer, Collection<Order> orders, Profile profile) {
}
