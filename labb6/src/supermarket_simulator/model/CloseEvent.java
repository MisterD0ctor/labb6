package supermarket_simulator.model;

import generic_simulator.EventQueue;
import generic_simulator.model.State;

public class CloseEvent extends SupermarketEvent {

	public CloseEvent(double time) {
		super(time);
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);

		store.close();
	}
}
