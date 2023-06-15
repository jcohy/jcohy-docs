package rsb.hateoas.hal;

import java.util.Date;
/*

@RequiredArgsConstructor
@Data
@AllArgsConstructor
class Order implements Comparable<Order> {

	private String id;

	private Date when;

	@Override
	public int compareTo(Order o) {
		return this.id.compareTo(o.id);
	}

}
*/

record Order(String id, Date when) {
}