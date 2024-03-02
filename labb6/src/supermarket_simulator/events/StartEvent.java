package supermarket_simulator.events;

import generic_simulator.EventQueue;
import generic_simulator.State;

public class StartEvent extends SupermarketEvent {

	public StartEvent() {
		super(0); // Super den kallar på konstruktorn i överklassen, Skapar ett event med tiden
					// noll.
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue); // Kör execute från SupermarketEvent

		eventQueue.enqueue(new ArivalEvent(store.nextArivalTime(), store.getCustomer())); //Skapar ett nytt arivalevent och vi hämtar tiden de eventet ska ske, 
		//sen skapar vi också en ny kund som de eventet ska ske för. Skickar vidare de eventet till eventkön
	}
}
