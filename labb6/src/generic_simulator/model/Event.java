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
	
	public void execute(State state, EventQueue eventQueue) {
		state.time = this.time;
	}
	
	public double time() {
		return time;
	}
}
