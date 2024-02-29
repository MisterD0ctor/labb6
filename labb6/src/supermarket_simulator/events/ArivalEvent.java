package supermarket_simulator.events;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;
import supermarket_simulator.StoreState;
import supermarket_simulator.Customer;

class ArivalEvent extends Event {
	
	public ArivalEvent(double time) {
		super(time);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		StoreState s = (StoreState)state;
		
		if (s.isClosed) {
			return;
		} else if (s.customerCount >= s.maxCustomerCount) {
			s.missedCustomerCount++;
			return;
		}
		
		Customer c = s.customerFactory.getCustomer();
		
		eventQueue.enqueue(new PickEvent(s.pickTimeProvider.next(), c));
		
		eventQueue.enqueue(new ArivalEvent(s.arivalTimeProvider.next()));		
	}
}