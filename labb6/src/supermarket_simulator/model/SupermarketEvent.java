package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.Event;
import generic_simulator.model.State;

/**
 * This is the parentclass of all the events of the supermarket
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class SupermarketEvent extends Event {

	/**
	 * 
	 * @param time The time where this event is supposed to happen
	 */
	public SupermarketEvent(double time) {
		super(time);
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		SupermarketState store = (SupermarketState) state; //Ser till att state är ett SupermarketState
		
		double deltaTime = (this.time - store.time()); // tids-deltan mellan förra och det nuvarande eventet
		
		// öka tiden kunder har stått i kassakön
		store.incrementQueueingTime(deltaTime * store.queueingCustomers());
		
		if (store.isOpen() || store.customerCount() > 0) { // slutar räkna när sista kunden betalt
			// öka tiden kassorna har stått lediga
			store.incrementIdleCheckoutTime(deltaTime * store.idleCheckouts());			
		}
		
		super.execute(state, eventQueue);
	}

}
