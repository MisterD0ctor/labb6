package supermarket_simulator.customers;

public class Customer {
	public final float id;
	
	Customer(float id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return Double.toString(id);
	}
}
