package supermarket_simulator.model;

import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;

public class SupermarketEvent extends Event {

	protected SupermarketState store; 
	
	public SupermarketEvent(double time) {
		super(time); 
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(State state, EventQueue eventQueue) {
		this.store = (SupermarketState)state;
		
		// Updatera view innan snabbköpet uppdateras
		store.notifyObservers(this);
		
		double deltaTime = (this.time - store.time); // tidsdeltan mellan förra och det nuvarande eventet
		
		store.incrementQueueingTime(deltaTime * store.queueingCustomers()); 
		store.incrementIdleCheckoutsTime(deltaTime * store.idleCheckouts());
		
		super.execute(state, eventQueue);
	}

}