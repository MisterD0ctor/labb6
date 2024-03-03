package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;

public class StopEvent extends generic_simulator.model.StopEvent {
	
	public StopEvent(double time) {
		super(time);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		SupermarketState store = (SupermarketState)state;
				
		store.notifyObservers(this);
	}

}
