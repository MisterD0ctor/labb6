package supermarket_simulator;

import generic_simulator.EventQueue;
import generic_simulator.Simulator;
import generic_simulator.StopEvent;
import supermarket_simulator.events.CloseEvent;
import supermarket_simulator.events.StartEvent;

public class Optimize {

	
	public StoreState runSim(int n, int m, double lambda, 
		double kMin, double kMax, double pMin, 
		double pMax, long f, double s) {
		
		StoreState state = new StoreState(
				n, 
				m,
				lambda,
				kMin,
				kMax,
				pMin,
				pMax,
				f);
		
		EventQueue eventQueue = new EventQueue();
		eventQueue.enqueue(new StartEvent());
		eventQueue.enqueue(new CloseEvent(s));
		eventQueue.enqueue(new StopEvent(999.0));
		Simulator sim = new Simulator(state, eventQueue);
		sim.run();
		return state;
	}

	
	//TODO 
	
	
	
	
}


//David TAR DENNA