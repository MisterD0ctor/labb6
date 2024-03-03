package generic_simulator.model;

import generic_simulator.EventQueue;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class Event {
	protected final double time;

	public Event(double time) {
		this.time = time;
	}

	@SuppressWarnings("deprecation")
	public void execute(State state, EventQueue eventQueue) {
		state.notifyObservers(this);
		state.setTime(this.time);
	}

	public double time() {
		return time;
	}
}
