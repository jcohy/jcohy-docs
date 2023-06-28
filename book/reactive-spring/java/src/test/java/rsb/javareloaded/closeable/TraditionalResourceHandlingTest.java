package rsb.javareloaded.closeable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

import static rsb.javareloaded.closeable.Utils.error;


/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/24:16:19
 * @since 2022.04.0
 */
public class TraditionalResourceHandlingTest {

    private final File file = Utils.setup();

    @Test
    void read() {
        var bufferedReader = (BufferedReader) null;
        try{
            bufferedReader = new BufferedReader(new FileReader(this.file));
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
        } finally {
            close(bufferedReader);
        }
    }


    private static void close(Reader reader) {
        if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                error(e);
            }
        }
    }

}
