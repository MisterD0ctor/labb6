package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;

public class PayEvent extends SupermarketEvent {
	
	public final Customer customer;
	
	public PayEvent(double time, Customer customer) {
		super(time);
		this.customer = customer;
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		// En kund har betalat så antalet kunder i snabbköpet minskar med ett
		store.decrementCustomers();
		store.incrementVisits();
		
		if (store.queueingCustomers() == 0) {
			// Inga kunder står i kö så en kassa blir ledig
			store.incrementIdleCheckouts();
		} else {
			// (Minst) en kund står i kö så kassan blir genast ockuperad igen
			Customer c = store.dequeueCustomer();
			// Ett nytt betalningsevent skapas för den kund som stod först i kassakön
			eventQueue.enqueue(new PayEvent(store.nextPayTime(), c));
		}
	}
}
