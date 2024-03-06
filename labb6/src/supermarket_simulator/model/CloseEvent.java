package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class CloseEvent extends SupermarketEvent {

	/**
	 * @param time The time where this event is supposed to happen
	 */
	public CloseEvent(double time) {
		super(time);
	}

	/**
	 * @param state The state execute effects
	 * @param eventQueue The queue that new events created by this method are added to
	 */
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue); 
		SupermarketState store = (SupermarketState) state; //Ser till att state är ett SupermarketState
		store.setIsOpen(false);
	}
	
}
