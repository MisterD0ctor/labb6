package generic_simulator.model;

import generic_simulator.EventQueue;

/**
 * A general event that affects a State at some time. This class is meant to be exetended by other events to add functionality.
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class Event {
	protected final double time;

	/**
	 * Crates an event with a specified time
	 * @param time The time where this event is supposed to happen
	 */
	public Event(double time) {
		this.time = time; //Hålla koll på vilken tid det är
	}

	/**
	 * Executes this event (makes it happen)
	 * @param state The state execute effects
	 * @param eventQueue The queue that new events created by this method are added to
	 */
	@SuppressWarnings("deprecation")
	public void execute(State state, EventQueue eventQueue) {
		state.notifyObservers(this); 
		state.setTime(this.time); //Sätter tiden på det nya eventet
	}

	/**
	 * @return The time of this event
	 */
	public double time() {
		return time;
	}
}
