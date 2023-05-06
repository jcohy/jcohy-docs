package com.jcohy.docs.reactive_spring.chapter4.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:39
 * @since 2022.04.0
 */
public record AsynchronousReadAttachment(File source, ByteBuffer buffer, ByteArrayOutputStream byteArrayOutputStream,
                                         long position) {
}
