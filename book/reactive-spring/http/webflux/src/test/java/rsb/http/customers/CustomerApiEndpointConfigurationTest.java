package rsb.http.customers;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;

@WebFluxTest
@Import({ CustomerApiEndpointConfiguration.class, CustomerHandler.class })
public class CustomerApiEndpointConfigurationTest extends AbstractRestBaseClass {

	@Override
	String rootUrl() {
		return "/fn";
	}

}