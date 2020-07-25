package pojo;


import java.sql.Date;
import java.util.List;

/**
 * Customer booking
 * @author Petrov_OlegYu
 */
public class Booking {
	private Long id;
	private String name;
	private String customerName;
	private State state;
	private Date deliverDate;
	private List<Product> products;

	public Booking(Long id, String name, String customerName, State state,
	               Date deliverDate, List<Product> products) {
		this.id = id;
		this.name = name;
		this.customerName = customerName;
		this.state = state;
		this.deliverDate = deliverDate;
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public enum State {
		NEW(),
		IN_DELIVERY(),
		COMPLETE(),
		CLOSED();
	}
}
