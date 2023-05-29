package rsb.http.customers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/23:15:45
 * @since 2022.04.0
 */
@Controller
public record CustomerViewController(CustomerRepository repository) {


    @GetMapping("/c/customers.php")
    String customersView(Model model) { // <1>
        var modelMap = Map.of("customers",repository.findAll(),"type","@Controller"); // <2>
        model.addAllAttributes(modelMap); // <3>
        return "customers"; // <4>
    }

}
