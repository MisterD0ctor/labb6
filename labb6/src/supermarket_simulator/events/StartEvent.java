package supermarket_simulator.events;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;
import supermarket_simulator.StoreState;

public class StartEvent extends Event {
	public StartEvent() {
		super(0);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		StoreState store = (StoreState)state;
		
		eventQueue.enqueue(new ArivalEvent(
				store.arivalTimeProvider.next(), 
				store.customerFactory.getCustomer()));
	}
}
