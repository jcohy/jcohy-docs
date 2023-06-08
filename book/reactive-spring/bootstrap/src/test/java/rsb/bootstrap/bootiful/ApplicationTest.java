package rsb.bootstrap.bootiful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import rsb.bootstrap.ApplicationContextAwareBaseClass;

@Slf4j
public class ApplicationTest extends ApplicationContextAwareBaseClass {

	@Override
	protected ConfigurableApplicationContext buildApplicationContext(Class<?> config, String... profiles) {
		return new SpringApplicationBuilder().profiles(profiles).sources(config).run();
	}

	@Override
	protected Class<?> getConfigurationClass() {
		return Application.class;
	}

}