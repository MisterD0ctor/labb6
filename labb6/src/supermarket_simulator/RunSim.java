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
		
		state.addObserver(view); // Lägger view som observer på Supermarket
		EventQueue eventQueue = new EventQueue(); //Skapar ny eventqueue
		eventQueue.enqueue(new StartEvent()); //Lägger till Startevent, Första eventet som kommer till Queue:n
		eventQueue.enqueue(new CloseEvent(Double.parseDouble(args[7]))); //När affären skall stänga
		eventQueue.enqueue(new StopEvent(999.0)); //Det som stoppar simulatorn från att fortskriva
		Simulator sim = new Simulator(state, eventQueue); //Skickar med det state som simuleringen skall utföras på och eventQueue (kön) som den skall innehålla
		sim.run(); //KÖR SIMULERING
	}

}
