package rsb.io.net;

import java.util.function.Consumer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/5:16:51
 * @since 2022.04.0
 */
public interface NetworkFileSync {

    void start(int port, Consumer<byte[]> byteHandler) throws Exception;

}
