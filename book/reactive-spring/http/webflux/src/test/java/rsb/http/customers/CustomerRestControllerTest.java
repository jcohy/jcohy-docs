package rsb.http.customers;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

@WebFluxTest
public class CustomerRestControllerTest extends AbstractRestBaseClass {

	@Override
	String rootUrl() {
		return "/rc";
	}

}