package pojo;

import java.util.List;

/**
 * Seller
 *
 * @author Petrov_OlegYu
 */
public class Seller {
	private Long id;
	private String name;
	private List<Booking> bookings;

	public Seller(Long id, String name, List<Booking> bookings) {
		this.id = id;
		this.name = name;
		this.bookings = bookings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
