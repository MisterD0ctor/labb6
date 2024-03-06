package generic_simulator.model;

import generic_simulator.EventQueue;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class StopEvent extends Event {

	/**
	 * Crates a stop event with a specified time
	 * @param time The time where this event is supposed to happen
	 */
	public StopEvent(double time) {
		super(time); // Kallar på konstruktorn till Event
	}

	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		state.stop();
	}
}
