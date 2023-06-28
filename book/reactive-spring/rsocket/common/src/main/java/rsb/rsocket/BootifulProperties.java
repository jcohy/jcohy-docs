package rsb.rsocket;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/30:14:59
 * @since 2022.04.0
 */

@ConfigurationProperties("bootiful") // <1>
public class BootifulProperties {

    private final RSocket rSocket = new RSocket();

    public RSocket getrSocket() {
        return rSocket;
    }

    public class RSocket {
        private String hostname = "localhost"; // <2>
        private int port = 8182; // <3>


        public String getHostname() {
            return hostname;
        }

        public RSocket setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public int getPort() {
            return port;
        }

        public RSocket setPort(int port) {
            this.port = port;
            return this;
        }
    }
}
