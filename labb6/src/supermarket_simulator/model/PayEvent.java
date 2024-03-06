package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class PayEvent extends SupermarketEvent {

	private final Customer customer;

	/**
	 * @param time The time where this event is supposed to happen
	 * @param customer The costumer that is arriving
	 */
	public PayEvent(double time, Customer customer) {
		super(time);
		this.customer = customer;
	}

	/**
	 * @param state The state execute effects
	 * @param eventQueue The queue that new events created by this method are added to
	 */
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		SupermarketState store = (SupermarketState) state; 

		// En kund har betalat så antalet kunder i snabbköpet minskar med ett
		store.decrementCustomerCount();
		store.incrementVisits();
		store.setLastCheckoutTime(this.time);

		if (store.queueingCustomers() == 0) {
			// Inga kunder står i kö så en kassa blir ledig
			store.incrementIdleCheckouts();
		} else {
			// (Minst) en kund står i kö så kassan blir genast ockuperad igen
			Customer c = store.dequeueCustomer(); // plocka första kunden från kassakön 
			// Ett nytt betalningsevent skapas för den kund som stod först i kassakön
			eventQueue.enqueue(new PayEvent(store.nextPayTime(), c));
		}
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
}
