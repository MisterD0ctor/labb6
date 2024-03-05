package generic_simulator.model;

import generic_simulator.EventQueue;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class Event {
	protected final double time;

	public Event(double time) {
		this.time = time; //Hålla koll på vilken tid det är
	}

	@SuppressWarnings("deprecation")
	public void execute(State state, EventQueue eventQueue) {
		state.notifyObservers(this); 
		state.setTime(this.time); //Sätter tiden på det nya eventet
	}

	public double time() {
		return time;
	}
}
