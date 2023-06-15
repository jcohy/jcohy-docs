package rsb.http.customers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/5/18:10:38
 * @since 2022.04.0
 */
@RestController // <1>
@RequestMapping(value = "/rc/customers") // <2>
record CustomerRestController(CustomerRepository repository) {

    @GetMapping("/{id}") // <3>
    Mono<Customer> byId(@PathVariable("id") String id) {
        return this.repository.findById(id);
    }

    @GetMapping // <4>
    Flux<Customer> all(){
        return this.repository.findAll();
    }

    @PostMapping // <5>
    Mono<ResponseEntity<?>> create(@RequestBody Customer customer) {
        return this.repository.save(customer)
                .map(customerEntity -> ResponseEntity
                        .created(URI.create("/rc/customers/"+ customerEntity.id()))
                        .build());
    }
}
