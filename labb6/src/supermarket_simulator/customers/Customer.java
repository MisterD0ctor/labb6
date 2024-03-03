package supermarket_simulator.customers;
/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach 
 */
public class Customer {
	private final int id;
	
	protected Customer(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return Integer.toString(id);
	}
}
