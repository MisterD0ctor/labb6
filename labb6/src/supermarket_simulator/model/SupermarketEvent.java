package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.Event;
import generic_simulator.model.State;

public class SupermarketEvent extends Event {

	protected SupermarketState store; 
	
	public SupermarketEvent(double time) {
		super(time); 
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(State state, EventQueue eventQueue) {
		this.store = (SupermarketState)state;
				
		double deltaTime = (this.time - store.time()); // tidsdeltan mellan förra och det nuvarande eventet
		
		store.incrementQueueingTime(deltaTime * store.queueingCustomers()); 
		store.incrementIdleCheckoutsTime(deltaTime * store.idleCheckouts());
		
		store.notifyObservers(this);
		
		super.execute(state, eventQueue);
	}

}
