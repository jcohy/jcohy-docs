package com.jcohy.docs.reactive_spring.chapter4;

import java.io.IOException;
import java.io.Reader;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/4:15:07
 * @since 2022.04.0
 */
public class Synchronous extends Reader {
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
