package supermarket_simulator.customers;

public class Customer {
	public final int id;
	
	protected Customer(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return Integer.toString(id);
	}
}
