package rsb.rsocket.metadata;

import org.springframework.util.MimeType;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/31:15:40
 * @since 2022.04.0
 */
public class Constants {

    // <1>
    public static final String CLIENT_ID_HEADER = "client-id";

    public static final String CLIENT_ID_VALUE = "message/x.bootiful." + CLIENT_ID_HEADER;

    public static final MimeType CLIENT_ID = MimeType.valueOf(CLIENT_ID_VALUE);

    // <2>
    public static final String LANG_HEADER = "lang";

    public static final String LANG_VALUE = "message/x.bootiful." + LANG_HEADER;

    public static final MimeType LANG = MimeType.valueOf(LANG_VALUE);
}
