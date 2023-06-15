package rsb.bootstrap.context;

import rsb.bootstrap.ApplicationContextAwareBaseClass;

public class ContextTest extends ApplicationContextAwareBaseClass {

	@Override
	protected Class<?> getConfigurationClass() {
		return Application.class;
	}

}
