package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.State;
import supermarket_simulator.customers.Customer;

public class ArivalEvent extends SupermarketEvent {

	public final Customer customer;

	public ArivalEvent(double time, Customer customer) {
		super(time);
		this.customer = customer;
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		if (store.isClosed()) {
			// Snabbköpet är stängt och inga
			return;
		} else if (store.isAtCapacity()) {
			// Snabbköpet är fullt och vi har missat en kund
			store.incrementMissedCustomers();
		} else {
			// Det finns plats i snabbköpet och ett framtida ankomstevent skapas
			store.incrementCustomers();			
			eventQueue.enqueue(new ArivalEvent(store.nextArivalTime(), store.newCustomer()));
		}
		
		// Ett framtida plockevent skapas för kunden som har anlänt
		eventQueue.enqueue(new PickEvent(store.nextPickTime(), this.customer));
	}
}