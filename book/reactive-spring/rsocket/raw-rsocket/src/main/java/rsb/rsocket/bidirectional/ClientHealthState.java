package rsb.rsocket.bidirectional;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:16:42
 * @since 2022.04.0
 */
public record ClientHealthState(String state) {

    public static final String STARTED = "started";

    public static final String STOPPED = "stopped";
}
