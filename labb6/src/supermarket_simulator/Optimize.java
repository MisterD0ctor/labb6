package supermarket_simulator;

import generic_simulator.EventQueue;
import generic_simulator.Simulator;
import generic_simulator.StopEvent;
import supermarket_simulator.events.CloseEvent;
import supermarket_simulator.events.StartEvent;

public class Optimize {

	
	public SupermarketState runSim(int n, int m, double lambda, double kMin, double kMax, double pMin, double pMax,
			long d, double s) { //Tar in 
		
		SupermarketState state = new SupermarketState(n, m, lambda, kMin, kMax, pMin, pMax, d);
		//
		
		EventQueue eventQueue = new EventQueue(); //Skapar ny instans av klassen EventQueue
		eventQueue.enqueue(new StartEvent());
		eventQueue.enqueue(new CloseEvent(s));
		eventQueue.enqueue(new StopEvent(999.0));
		Simulator sim = new Simulator(state, eventQueue); //Skickar med det state som simuleringen skall utföras på och eventQueue (kön) som den skall innehålla
		sim.run();
		return state;
	}

	
	//TODO Metod 2 Optimering av metod 1 via en loop.
	
	public int methodtwo () {
		int miss = 1;
		int i = 0;
		for (i = 0; miss > 0; i++){
			miss = runSim(i, 40, 5.0, 0.25, 1.0, 0.25, 1.0, Long.parseLong(String.valueOf(123456789.0)), 16.0).missedCustomers();
		}
		return i;
	}
	
	
	
}


//David TAR DENNA