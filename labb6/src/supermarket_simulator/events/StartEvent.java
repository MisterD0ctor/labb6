package supermarket_simulator;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;

class StartEvent extends Event {
	public StartEvent() {
		super(0f);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		eventQueue.enqueue(null);
	}
}
