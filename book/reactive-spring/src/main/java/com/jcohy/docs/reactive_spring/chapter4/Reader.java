package com.jcohy.docs.reactive_spring.chapter4;

import java.io.File;
import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/4:15:16
 * @since 2022.04.0
 */
public interface Reader {

    void read(File file , Consumer<Bytes> consumer, Runnable f)
}
