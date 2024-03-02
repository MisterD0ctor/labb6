package supermarket_simulator.customers;

public class CustomerFactory {
	private int customerCount;
	
	public CustomerFactory() {
		customerCount = 0;
	}
	
	public Customer getCustomer() {
		return new Customer(customerCount++);
	}
}
