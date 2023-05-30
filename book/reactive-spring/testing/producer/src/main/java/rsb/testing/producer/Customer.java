package rsb.testing.producer;

import org.springframework.data.annotation.Id;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/29:15:17
 * @since 2022.04.0
 */
// <1>
public record Customer(@Id String id, String name) {
}
