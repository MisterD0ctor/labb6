package generic_simulator;

public class Event {
	public final float time;
	
	public Event(float time) {
		this.time = time;
	}
	
	public void execute(EventQueue eventQueue, State state) {
		state.time = this.time;
	}
}
