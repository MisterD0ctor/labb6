package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class ArivalEvent extends SupermarketEvent {

	public final Customer customer;

	public ArivalEvent(double time, Customer customer) {
		super(time);
		this.customer = customer;
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);

		if (!store.isOpen()) {
			return; // Snabbköpet är stängt och inget nytt ankomstevent skapas
		}

		store.incrementAttemptedVisits(); // en kund försöker besöka snabbköpet

		if (store.isAtCapacity()) {
			// Snabbköpet är fullt och vi har missat en kund
			store.incrementMissedCustomers();
		} else {
			// Det finns plats i snabbköpet och ett framtida ankomstevent skapas
			store.incrementCustomers();
			// Ett framtida plockevent skapas för kunden som har anlänt
			eventQueue.enqueue(new PickEvent(store.nextPickTime(), this.customer));
		}

		// En ny kund anländer i framtiden
		eventQueue.enqueue(new ArivalEvent(store.nextArivalTime(), store.newCustomer()));
	}
}