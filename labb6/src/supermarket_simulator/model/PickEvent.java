package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.Event;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;

/**
 * This represents the event of a costumer picking their groceries and then going to a checkout
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class PickEvent extends SupermarketEvent {

	private final Customer customer;

	/**
	 * @param time The time where this event is supposed to happen
	 * @param customer The costumer that is picking
	 */
	public PickEvent(double time, Customer customer) {
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
		
		// Kontrollera om det finns lediga kassor
		if (store.idleCheckouts() > 0) {
			// Minskar antalet lediga kassor eftersom en till är upptagen nu
			store.decrementIdleCheckouts();

			eventQueue.enqueue(new PayEvent(store.nextPayTime(), customer));
		} else {
			// Alla kassor är upptagna, ställ kunden i kassakön
			store.enqueueCustomer(customer);
		}
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
}
