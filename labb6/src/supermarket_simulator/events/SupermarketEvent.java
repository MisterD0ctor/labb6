package supermarket_simulator.events;

import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;
import supermarket_simulator.SupermarketState;

public class SupermarketEvent extends Event {

	SupermarketState store;
	
	public SupermarketEvent(double time) {
		super(time);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(State state, EventQueue eventQueue) {
		this.store = (SupermarketState)state;
		
		// Updatera view innan snabbköpet uppdateras
		store.notifyObservers(this);
		
		/* Kötidsberäkningen görs innan snabbköpets tid uppdateras 
		 * då den gamla tiden krävs för att beräkna tidsskillnaden */
		store.incrementQueueingTime((this.time - store.time) * store.queueingCustomers());
		
		super.execute(state, eventQueue);
	}

}
