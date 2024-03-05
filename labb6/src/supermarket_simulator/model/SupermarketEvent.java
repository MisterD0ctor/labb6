package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.Event;
import generic_simulator.model.State;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class SupermarketEvent extends Event {

	public SupermarketEvent(double time) {
		super(time);
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		SupermarketState store = (SupermarketState) state;
		
		double deltaTime = (this.time - store.time()); // tids-deltan mellan förra och det nuvarande eventet
		
		if (store.isOpen() || store.isClosing()) {
			// öka tiden kunder har stått i kassakön
			store.incrementQueueingTime(deltaTime * store.queueingCustomers());
			// öka tiden kassorna har stått lediga
			store.incrementIdleCheckoutTime(deltaTime * store.idleCheckouts());
			
			if (store.isClosing()) {
				store.setClosed();
			}
		}
		
		super.execute(state, eventQueue);
	}

}
