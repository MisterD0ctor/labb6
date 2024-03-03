package supermarket_simulator;

import generic_simulator.*;
import supermarket_simulator.model.*;
import supermarket_simulator.view.SupermarketView;

public class RunSim {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) { 

		int openCheckouts = Integer.parseInt(args[0]); //Skapar antalet kassor, ändrar om args till int.
		int customerCapacity = Integer.parseInt(args[1]); //Hur många som får finnas i butiken
		double arivalFrequency = Double.parseDouble(args[2]); //Hur ofta nya kunder kommer, medel för frekvensen över hur många som går in i butiken... Ifall arrivalfrequency är 4 kommer en costumer per kvart
		double minPickTime = Double.parseDouble(args[3]); //Lägsta tiden det tar för en kund att plocka ihop sina varor
		double maxPickTime = Double.parseDouble(args[4]); 
		double minPayTime = Double.parseDouble(args[5]); //Lägsta tiden det tar för en kund att betala för sina varor
		double maxPayTime = Double.parseDouble(args[6]);
		double closeTime = Double.parseDouble(args[7]);
		long seed = Long.parseLong(args[8]); //Fröet som bestämmer hur slumptalen ska genereras, alla argument kommer i en array av åtta element
		
		SupermarketState state = new SupermarketState(openCheckouts, customerCapacity, arivalFrequency, minPickTime,
				maxPickTime, minPayTime, maxPayTime, seed); // Skickar med alla parametrar till state
		
		SupermarketView view = new SupermarketView(openCheckouts, customerCapacity, arivalFrequency, minPickTime,
				maxPickTime, minPayTime, maxPayTime, seed); // Skickar med alla parametrar till View så att de kan skrivas ut i konsolen
		
		state.addObserver(view); // Lägger view som observer på Supermarket
		EventQueue eventQueue = new EventQueue(); //Skapar ny eventqueue
		eventQueue.enqueue(new StartEvent()); //Lägger till Startevent, Första eventet som kommer till Queue:n
		eventQueue.enqueue(new CloseEvent(closeTime)); //När affären skall stänga
		eventQueue.enqueue(new StopEvent(999.0)); //Det som stoppar simulatorn från att fortskriva
		Simulator sim = new Simulator(state, eventQueue); //Skickar med det state som simuleringen skall utföras på och eventQueue (kön) som den skall innehålla
		sim.run(); //KÖR SIMULERING
	}

}
