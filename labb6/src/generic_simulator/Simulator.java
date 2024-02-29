package generic_simulator;

public class Simulator {
	private State state;
	private EventQueue eventQueue;
	
	public Simulator(State state, EventQueue eventQueue) {
		this.state = state;
		this.eventQueue = eventQueue;
	}
	
	public void run() {
		while (state.running) {
			Event event = eventQueue.dequeue();
			
			if (event != null) {
				event.execute(state, eventQueue);				
			}
		}
	}
}
