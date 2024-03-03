package supermarket_simulator;

import generic_simulator.EventQueue;
import generic_simulator.Simulator;
import generic_simulator.StopEvent;
import supermarket_simulator.model.CloseEvent;
import supermarket_simulator.model.StartEvent;
import supermarket_simulator.model.SupermarketState;

public class Optimize {

	public static void main (String[] args) {
		Optimize op = new Optimize(); 
		System.out.print(op.methodtwo());
	}
	
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

	
	//Metod 2
	
	public int methodtwo () {
		int miss = 1;
		int i;
		for (i = 2; miss > 0; i++){
			miss = runSim(i, 100, 5.0, 0.25, 1.0, 0.25, 1.0, Long.parseLong(String.valueOf(123456789)), 16.0).missedCustomers();
		}
		return i;
	}
	
	
	//Metod 3 - ska starta
	
}


//David TAR DENNA