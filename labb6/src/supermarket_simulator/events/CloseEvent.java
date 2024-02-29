package supermarket_simulator.events;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;
import supermarket_simulator.StoreState;

public class CloseEvent extends Event {

	public CloseEvent(double time) {
		super(time);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		StoreState s = (StoreState)state;
		s.isClosed = true;
	}
}
