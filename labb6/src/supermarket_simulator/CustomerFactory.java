package supermarket_simulator;

class CustomerFactory {
	private float customerCount;
	
	public CustomerFactory() {
		customerCount = 0f;
	}
	
	public Customer CreateCustomer() {
		return new Customer(customerCount++);
	}
}
