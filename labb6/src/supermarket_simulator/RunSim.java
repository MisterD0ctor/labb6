package supermarket_simulator;

import generic_simulator.*;
import supermarket_simulator.events.*;

public class RunSim {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		int openCheckouts = Integer.parseInt(args[1]);
		int customerCapacity = Integer.parseInt(args[1]);
		double arivalFrequency = Double.parseDouble(args[2]);
		double minPickTime = Double.parseDouble(args[3]);
		double maxPickTime = Double.parseDouble(args[4]);
		double minPayTime = Double.parseDouble(args[5]);
		double maxPayTime = Double.parseDouble(args[6]);
		long seed = Integer.parseInt(args[8]);

		SupermarketState state = new SupermarketState(openCheckouts, customerCapacity, arivalFrequency, minPickTime,
				maxPickTime, minPayTime, maxPayTime, seed);

		SupermarketView view = new SupermarketView(openCheckouts, customerCapacity, arivalFrequency, minPickTime,
				maxPickTime, minPayTime, maxPayTime, seed);
		
		state.addObserver(view);
		EventQueue eventQueue = new EventQueue();
		eventQueue.enqueue(new StartEvent());
		eventQueue.enqueue(new CloseEvent(Double.parseDouble(args[7])));
		eventQueue.enqueue(new StopEvent(999.0));
		Simulator sim = new Simulator(state, eventQueue);
		sim.run();
	}

}
