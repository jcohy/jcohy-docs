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

package org.springframework.boot.docs.messaging.jms.receiving.custom

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import javax.jms.ConnectionFactory

@Configuration(proxyBeanMethods = false)
class MyJmsConfiguration {

	@Bean
	fun myFactory(configurer: DefaultJmsListenerContainerFactoryConfigurer): DefaultJmsListenerContainerFactory {
		val factory = DefaultJmsListenerContainerFactory()
		val connectionFactory = getCustomConnectionFactory()
		configurer.configure(factory, connectionFactory)
		factory.setMessageConverter(MyMessageConverter())
		return factory
	}

	fun getCustomConnectionFactory() : ConnectionFactory? {
		return /**/ null
	}

}
