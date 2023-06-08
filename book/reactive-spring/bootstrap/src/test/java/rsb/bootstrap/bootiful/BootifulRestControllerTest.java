package rsb.bootstrap.bootiful;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rsb.bootstrap.Customer;
import rsb.bootstrap.CustomerService;

import java.util.Collections;

@WebMvcTest
@Import(BootifulRestController.class)
public class BootifulRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	@Test
	public void fetchAllCustomers() throws Exception {
		Mockito.when(this.customerService.findAll()).thenReturn(Collections.singletonList(new Customer(1L, "Jane")));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/customers")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].name").value("Jane"));
	}

}
