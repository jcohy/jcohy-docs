package rsb.hateoas.hal;/*
 * package rsb.hateoas.hal;
 *
 * import lombok.RequiredArgsConstructor; import org.reactivestreams.Publisher; import
 * org.springframework.boot.SpringApplication; import
 * org.springframework.boot.autoconfigure.SpringBootApplication; import
 * org.springframework.hateoas.CollectionModel; import org.springframework.hateoas.Link;
 * import org.springframework.hateoas.RepresentationModel; import
 * org.springframework.hateoas.server.RepresentationModelAssembler; import
 * org.springframework.stereotype.Component; import
 * org.springframework.stereotype.Repository; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController; import
 * reactor.core.publisher.Flux; import reactor.core.publisher.Mono;
 *
 * import java.util.Date; import java.util.Random; import java.util.Set; import
 * java.util.UUID; import java.util.stream.Collectors; import java.util.stream.IntStream;
 * import java.util.stream.StreamSupport;
 *
 * // https://tools.ietf.org/html/draft-kelly-json-hal-08
 *
 * @SpringBootApplication public class HalApplication {
 *
 * public static void main(String args[]) { SpringApplication.run(HalApplication.class,
 * args); }
 *
 * }
 *
 * @Repository class CustomerRepository {
 *
 * private final Flux<Customer> customers;
 *
 * CustomerRepository() {
 *
 * var customerSet = Set.of(new Customer("Jane"), new Customer("Zhen"), new
 * Customer("Jakub"), new Customer("Dave"));
 *
 * this.customers = Flux.fromIterable(customerSet).map(customer -> {
 *
 * IntStream.range(0, new Random().nextInt(5)).forEach( ctr ->
 * customer.addOrder(UUID.randomUUID().toString(), new Date()));
 *
 * return customer; });
 *
 * }
 *
 * Mono<Customer> findById(String id) { return this.customers.filter(c ->
 * c.getId().equalsIgnoreCase(id)).single(); }
 *
 * Flux<Customer> findAll() { return this.customers; }
 *
 * }
 *
 * @RestController
 *
 * @RequestMapping("/customers")
 *
 * @RequiredArgsConstructor class CustomerRestController {
 *
 * private final CustomerRepository repository;
 *
 * @GetMapping("/{id}") Publisher<Customer> getById(@PathVariable String id) { return
 * this.repository.findById(id); }
 */
/*
 * @GetMapping Publisher<CollectionModel<EntityModel<Customer>>> all() { var controller =
 * methodOn(CustomerRestController.class); return this.repository.findAll() .collectList()
 * .flatMap(customers -> linkTo()); }
 *//*
	
	
	*/
/*
 *
 * @GetMapping("/customers") Mono<CollectionModel<EntityModel<Order>>> all() {
 *
 * var controller = methodOn(HalRestController.class);
 *
 * return repository.findAll() .collectList() .flatMap(resources ->
 * linkTo(controller.all()).withSelfRel() .andAffordance(controller.newEmployee(null))
 * .andAffordance(controller.search(null, null)) .toMono() .map(selfLink -> new
 * CollectionModel<>(resources, selfLink))); }
 *//*
	 *
	 *
	 * }
	 *
	 * class CustomerRepresentationModel extends
	 * RepresentationModel<CustomerRepresentationModel> {
	 *
	 * private final Customer customer;
	 *
	 * CustomerRepresentationModel(Customer customer) { this.customer = customer; }
	 *
	 * }
	 *
	 * @Component class CustomerRepresentationModelAssembler implements
	 * RepresentationModelAssembler<Customer, CustomerRepresentationModel> {
	 *
	 * @Override public CustomerRepresentationModel toModel(Customer customer) { return
	 * new CustomerRepresentationModel(customer); }
	 *
	 * @Override public CollectionModel<CustomerRepresentationModel> toCollectionModel(
	 * Iterable<? extends Customer> entities) {
	 *
	 * var stream = StreamSupport.stream(entities.spliterator(), true)
	 * .map(CustomerRepresentationModel::new);
	 *
	 * return new CollectionModel<>(stream.collect(Collectors.toSet()), new
	 * Link("http://adobe.com", "adobe-home-page")); }
	 *
	 * }
	 */
