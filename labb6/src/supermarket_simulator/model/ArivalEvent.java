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
			return;
		} else if (store.isAtCapacity()) {
			store.incrementMissedCustomers();
		} else {
			store.incrementCustomers();			
			eventQueue.enqueue(new ArivalEvent(store.nextArivalTime(), store.newCustomer()));
		}
		
		eventQueue.enqueue(new PickEvent(store.nextPickTime(), this.customer));
	}
}