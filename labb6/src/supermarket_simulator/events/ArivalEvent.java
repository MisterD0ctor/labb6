package supermarket_simulator.events;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;
import supermarket_simulator.StoreState;
import supermarket_simulator.Customer;

class ArivalEvent extends Event {
	
	public Customer customer;
	
	public ArivalEvent(double time, Customer customer) {
		super(time);
		this.customer = customer;
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		StoreState store = (StoreState)state;
		
		eventQueue.enqueue(new PickEvent(
				store.pickTimeProvider.next(), 
				this.customer));
		
		if (store.isClosed) {
			return;
		} else if (store.customerCount >= store.maxCustomerCount) {
			store.missedCustomerCount++;
			return;
		}
		
		eventQueue.enqueue(new ArivalEvent(
				store.arivalTimeProvider.next(), 
				store.customerFactory.getCustomer()));		
	}
}