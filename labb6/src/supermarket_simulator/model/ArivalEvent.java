package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;

/**
 * This represents the event of a costumer arriving to the supermarket
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class ArivalEvent extends SupermarketEvent {

	private final Customer customer;

	/**
	 * @param time The time where this event is supposed to happen
	 * @param customer The costumer that is arriving
	 */
	public ArivalEvent(double time, Customer customer) {
		super(time);
		this.customer = customer;
	}

	/**
	 * @param state The state execute effects
	 * @param EventQueue The queue that new events created by this method are added to
	 */
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		SupermarketState store = (SupermarketState) state;

		if (!store.isOpen()) {
			return; // Snabbköpet är stängt och inget nytt ankomstevent skapas
		}

		store.incrementAttemptedVisits(); // en kund försöker besöka snabbköpet

		if (store.isAtCapacity()) {
			// Snabbköpet är fullt och vi har missat en kund
			store.incrementMissedCustomers();
		} else {
			// Det finns plats i snabbköpet och ett framtida ankomstevent skapas
			store.incrementCustomerCount();
			// Ett framtida plockevent skapas för kunden som har anlänt
			eventQueue.enqueue(new PickEvent(store.nextPickTime(), this.customer));
		}
		
		// En ny kund anländer i framtiden
		eventQueue.enqueue(new ArivalEvent(store.nextArivalTime(), store.newCustomer()));
	}
	
	/**
	 * 
	 * @return the costumer of this event
	 */
	public Customer getCustomer() {
		return this.customer;
	}
	
}