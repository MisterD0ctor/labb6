package supermarket_simulator;
import java.util.Random;
import generic_simulator.EventQueue;
import generic_simulator.Simulator;
import supermarket_simulator.model.CloseEvent;
import supermarket_simulator.model.StartEvent;
import supermarket_simulator.model.StopEvent;
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
		eventQueue.enqueue(new StartEvent(0));
		eventQueue.enqueue(new CloseEvent(s));
		eventQueue.enqueue(new StopEvent(999.0));
		Simulator sim = new Simulator(state, eventQueue); //Skickar med det state som simuleringen skall utföras på och eventQueue (kön) som den skall innehålla
		sim.run();
		return state;
	}

	
	//Metod 2 SAKNAR BINÄRSÖKNING
	/*
	public int methodtwo () {
		int miss = 1;
		int i;
		for (i = 1; miss > 0; i++){
			miss = runSim(i, 5, 1.0, 2.0, 3.0, 0.5, 1.0, 1234, 16.0).missedCustomers();
		}
		return i;
	}*/
	public int methodtwo() {
	    int miss = 1;
	    int i = 1;  // Starta med 1 eftersom du inte kan ha 0 kassor.
	    while (miss > 0) {
	        // Kör simuleringen med 'i' kassor.
	        miss = runSim(i, 7, 2.0, 2.0, 3.0, 0.5, 1.0, 1234, 16.0).missedCustomers();
	        i++;  // Öka antalet kassor för nästa iteration.
	    }
	    return i + 1;  // Returnera 'i - 1' eftersom 'i' har ökat även när miss = 0.
	}
	
	
	//Metod 3 - ska starta
	
	public int methodthree (int f) {
		Random random = new Random(f);
        int highestMin = Integer.MIN_VALUE; // Initial highest minimum value
        int consecutiveStableCount = 0;
		
        while (consecutiveStableCount < 100) { // Loop until 100 consecutive stable iterations
            int min = methodtwo(); // Run methodtwo
            if (min > highestMin) { // Check if new highest minimum found
                highestMin = min;
                consecutiveStableCount = 0; // Reset consecutive stable count
            } else {
                consecutiveStableCount++; // Increment consecutive stable count
            }
        }

        return highestMin;
    }
	
	
}


//David TAR DENNA*/