package supermarket_simulator.customers;

public class Customer {
	public final int id;
	
	Customer(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return Integer.toString(id);
	}
}
