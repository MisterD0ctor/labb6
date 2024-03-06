package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;

/**
 * This represents the event of the supermarket closing 
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class CloseEvent extends SupermarketEvent {

	/**
	 * @param time The time where this event is supposed to happen
	 */
	public CloseEvent(double time) {
		super(time);
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue); 
		SupermarketState store = (SupermarketState) state; //Ser till att state är ett SupermarketState
		store.setIsOpen(false);
	}
	
}
