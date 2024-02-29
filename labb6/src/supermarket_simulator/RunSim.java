package supermarket_simulator;
import generic_simulator.*;
import supermarket_simulator.events.*;

public class RunSim {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		StoreState state = new StoreState(
				Integer.parseInt(args[0]),
				Integer.parseInt(args[1]),
				Double.parseDouble(args[2]),
				Double.parseDouble(args[3]),
				Double.parseDouble(args[4]),
				Double.parseDouble(args[5]),
				Double.parseDouble(args[6]),
				Integer.parseInt(args[8]));
		
		StoreView view = new StoreView();
		state.addObserver(view);
		EventQueue eventQueue = new EventQueue();
		eventQueue.enqueue(new StartEvent());
		eventQueue.enqueue(new CloseEvent(Double.parseDouble(args[7])));
		eventQueue.enqueue(new StopEvent(999.0));
		Simulator sim = new Simulator(state, eventQueue);
		sim.run();
	}

}
