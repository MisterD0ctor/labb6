package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;
/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach 
 */
public class StartEvent extends SupermarketEvent {

	public StartEvent(double time) {
		super(time); // Super den kallar på konstruktorn i överklassen
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue); // Kör execute från SupermarketEvent

		if (!store.isAtCapacity()) {
			eventQueue.enqueue(new ArivalEvent(store.nextArivalTime(), store.newCustomer())); // Skapar ett nytt
																								// arivalevent och vi
																								// hämtar tiden de
																								// eventet ska ske,
		}
		// sen skapar vi också en ny kund som de eventet ska ske för. Skickar vidare de
		// eventet till eventkön
	}
}
