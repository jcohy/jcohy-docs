package com.jcohy.docs.reactive_spring.chapter7.webclient.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/24:15:26
 * @since 2022.04.0
 */
@ConfigurationProperties(prefix = "client")
public class ClientProperties {

    public ClientProperties(Http http) {
        this.http = http;
    }

    public ClientProperties() {
    }

    private Http http = new Http();

    public static class Http{

        private Basic basic = new Basic();

        // <1>
        private String rootUrl;

        public static class Basic {
            private String username;

            // <2>
            private String password;

            public String getUsername() {
                return username;
            }

            public Basic setUsername(String username) {
                this.username = username;
                return this;
            }

            public String getPassword() {
                return password;
            }

            public Basic setPassword(String password) {
                this.password = password;
                return this;
            }
        }

        public Basic getBasic() {
            return basic;
        }

        public Http setBasic(Basic basic) {
            this.basic = basic;
            return this;
        }

        public String getRootUrl() {
            return rootUrl;
        }

        public Http setRootUrl(String rootUrl) {
            this.rootUrl = rootUrl;
            return this;
        }
    }

    public Http getHttp() {
        return http;
    }

    public ClientProperties setHttp(Http http) {
        this.http = http;
        return this;
    }
}
