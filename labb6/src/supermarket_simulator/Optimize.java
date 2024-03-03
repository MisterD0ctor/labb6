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


//David TAR DENNA