package supermarket_simulator.customers;

/**
 * A customer in the supermarket
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class Customer {
	private final int id;

	/**
	 * 
	 * @param id The id of the customer
	 */
	protected Customer(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The id of the customer
	 */
	public int id() {
		return id;
	}
	
	@Override
	public String toString() {
		return Integer.toString(id); // Returnerar kundens id som en sträng
	}
}
