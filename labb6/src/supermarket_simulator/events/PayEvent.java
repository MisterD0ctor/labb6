package supermarket_simulator.events;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;
import supermarket_simulator.StoreState;
import supermarket_simulator.Customer;

public class PayEvent extends Event {
	
	private Customer customer;
	
	public PayEvent(double time, Customer customer) {
		super(time);
		this.customer = customer;
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		StoreState store = (StoreState)state;
		
		store.customerCount--;
		store.payCount++;
		
		if (store.checkoutQueue.isEmpty()) {
			store.availableCheckoutsCount++;
			return;
		}
		
		Customer c = store.checkoutQueue.pop();
		store.totalQueueTime += time; 
		
		eventQueue.enqueue(new PayEvent(store.payTimeProvider.next(), c));
	}
}
