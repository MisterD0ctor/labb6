package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.Event;
import generic_simulator.model.State;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class SupermarketEvent extends Event {

	protected SupermarketState store;

	public SupermarketEvent(double time) {
		super(time);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(State state, EventQueue eventQueue) {
		this.store = (SupermarketState) state;
		
		double deltaTime = (this.time - store.time()); // tids-deltan mellan förra och det nuvarande eventet
		
		if (store.isOpen() || store.isClosing()) {
			store.incrementQueueingTime(deltaTime * store.queueingCustomers());
			store.incrementIdleCheckoutTime(deltaTime * store.idleCheckouts());
			
			if (this.store.isClosing()) {
				this.store.setClosed();
			}
		}
		
		super.execute(state, eventQueue);
	}

}
