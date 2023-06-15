package rsb.bootstrap;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.atomic.AtomicReference;

public abstract class ApplicationContextAwareBaseClass extends BaseClass {

	private final AtomicReference<ConfigurableApplicationContext> reference = new AtomicReference<>();

	protected ConfigurableApplicationContext getCurrentApplicationContext() {
		if (this.reference.get() == null) {
			var applicationContext = this.buildApplicationContext(getConfigurationClass(), "prod");
			this.reference.set(applicationContext);
		}
		return this.reference.get();
	}

	@Override
	public CustomerService getCustomerService() {
		return getCurrentApplicationContext().getBean(CustomerService.class);
	}

	protected ConfigurableApplicationContext buildApplicationContext(Class<?> config, String... profiles) {
		var context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles(profiles);
		context.register(config);
		context.refresh();
		context.start();
		return context;
	}

	protected abstract Class<?> getConfigurationClass();

}
