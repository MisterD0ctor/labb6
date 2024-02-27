package generic_simulator;

public class Event {
	public final double time;
	
	public Event(double time) {
		this.time = time;
	}
	
	public void execute(State state, EventQueue eventQueue) {
		state.time = this.time;
	}
}
