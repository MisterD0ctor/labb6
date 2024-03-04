package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class CloseEvent extends SupermarketEvent {

	public CloseEvent(double time) {
		super(time);
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		SupermarketState store = (SupermarketState) state;
		store.beginClosing();
	}
	
}
