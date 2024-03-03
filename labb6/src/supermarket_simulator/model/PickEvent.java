package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.Event;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;

public class PickEvent extends SupermarketEvent {

	public final Customer customer;

	public PickEvent(double time, Customer customer) {
		super(time);
		this.customer = customer;
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);

		// Kontrollera om det finns lediga kassor
		if (store.idleCheckouts() > 0) {
			// Minskar antalet lediga kassor eftersom en till är upptagen nu
			store.decrementIdleCheckouts();

			Event payEvent = new PayEvent(store.nextPayTime(), customer);
			eventQueue.enqueue(payEvent);
		} else {
			// Alla kassor är upptagna, ställ kunden i kassakön
			store.enqueueCustomer(customer);
		}
	}
}
