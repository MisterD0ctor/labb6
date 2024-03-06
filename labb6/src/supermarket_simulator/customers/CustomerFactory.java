package supermarket_simulator.customers;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class CustomerFactory {
	private int customerCount; //Antalet kunder som fabriken har skapat

	public CustomerFactory() {
		customerCount = 0;
	}

	/**
	 * 
	 * @return A new customer with a unique id
	 */
	public Customer getCustomer() {
		return new Customer(customerCount++);
	}
}
