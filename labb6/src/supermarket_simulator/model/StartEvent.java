package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;

/**
 * This represents the event of the supermarket opening and taking in their first costumer
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class StartEvent extends SupermarketEvent {

	/**
	 * @param time The time where this event is supposed to happen
	 */
	public StartEvent(double time) {
		super(time); // Super den kallar på konstruktorn i överklassen
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue); // Kör execute från SupermarketEvent

		SupermarketState store = (SupermarketState) state;
		
		if (!store.isAtCapacity()) {
			// Skapa ett nyt arivalevent och vi hämta tiden de eventet ska ske, sen skapar
			// vi också en ny kund som de eventet ska ske för. 
			eventQueue.enqueue(new ArivalEvent(store.nextArivalTime(), store.newCustomer()));
		}
	}
}
