package supermarket_simulator.customers;

public class CustomerFactory {
	private float customerCount;
	
	public CustomerFactory() {
		customerCount = 0f;
	}
	
	public Customer getCustomer() {
		return new Customer(customerCount++);
	}
}
