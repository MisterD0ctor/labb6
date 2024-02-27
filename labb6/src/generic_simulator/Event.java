package generic_simulator;

public class Event {
	public final float time;
	
	public Event(float time) {
		this.time = time;
	}
	
	public void execute(State state, EventQueue eventQueue) {
		state.time = this.time;
	}
}
