package com.jcohy.docs.reactive_spring.chapter6.common;

import org.springframework.data.annotation.Id;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/9:15:00
 * @since 2022.04.0
 */
public record Customer(@Id Integer id, String email) {
}
