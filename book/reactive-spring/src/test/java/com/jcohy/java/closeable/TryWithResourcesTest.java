package com.jcohy.java.closeable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.jcohy.java.closeable.Utils.error;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/26:15:25
 * @since 2022.04.0
 */
public class TryWithResourcesTest {

    private final File file = Utils.setup();

    @Test
    void tryWithresources() {
        try(var fileReader = new FileReader(this.file); var bufferedReader = new BufferedReader(fileReader)) {
            var stringBuilder = new StringBuilder();
            var line = (String) null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            var contents = stringBuilder.toString().trim();
            Assertions.assertEquals(contents,Utils.CONTENTS);
        } catch (IOException e) {
            error(e);
        }
    }
}
