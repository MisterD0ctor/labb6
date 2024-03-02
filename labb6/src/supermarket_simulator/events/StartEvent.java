package supermarket_simulator.events;

import generic_simulator.EventQueue;
import generic_simulator.State;

public class StartEvent extends SupermarketEvent {
	
	public StartEvent() {
		super(0);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
				
		eventQueue.enqueue(new ArivalEvent(
				store.nextArivalTime(), 
				store.getCustomer()));
	}
}
