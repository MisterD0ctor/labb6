package supermarket_simulator;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;

class ArivalEvent extends Event {
	public ArivalEvent(float time) {
		super(time);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		eventQueue.enqueue(null);
	}
}