/*
 * Copyright 2012-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.docs.howto.webserver.enablemultipleconnectorsintomcat

import org.apache.catalina.connector.Connector
import org.apache.coyote.http11.Http11NioProtocol
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ResourceUtils
import java.io.IOException

@Configuration(proxyBeanMethods = false)
class MyTomcatConfiguration {

	@Bean
	fun sslConnectorCustomizer(): WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
		return WebServerFactoryCustomizer { tomcat: TomcatServletWebServerFactory ->
			tomcat.addAdditionalTomcatConnectors(
				createSslConnector()
			)
		}
	}

	private fun createSslConnector(): Connector {
		val connector = Connector("org.apache.coyote.http11.Http11NioProtocol")
		val protocol = connector.protocolHandler as Http11NioProtocol
		return try {
			val keystore = ResourceUtils.getURL("keystore")
			val truststore = ResourceUtils.getURL("truststore")
			connector.scheme = "https"
			connector.secure = true
			connector.port = 8443
			protocol.isSSLEnabled = true
			protocol.keystoreFile = keystore.toString()
			protocol.keystorePass = "changeit"
			protocol.truststoreFile = truststore.toString()
			protocol.truststorePass = "changeit"
			protocol.keyAlias = "apitester"
			connector
		} catch (ex: IOException) {
			throw IllegalStateException("Fail to create ssl connector", ex)
		}
	}

}
