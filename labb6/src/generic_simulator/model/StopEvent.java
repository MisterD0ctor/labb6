package generic_simulator.model;

import generic_simulator.EventQueue;

public class StopEvent extends Event {
	
	public StopEvent(double time) {
		super(time); // Kallar på konstruktorn till Event
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue); // Keep counting time?
		
		state.stop();
	}
}
