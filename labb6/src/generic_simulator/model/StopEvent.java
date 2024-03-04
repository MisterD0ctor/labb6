package generic_simulator.model;

import generic_simulator.EventQueue;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class StopEvent extends Event {

	public StopEvent(double time) {
		super(time); // Kallar på konstruktorn till Event
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		state.stop();
	}
}
