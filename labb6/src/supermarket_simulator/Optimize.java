package supermarket_simulator;

import java.util.Random;
import generic_simulator.EventQueue;
import generic_simulator.Simulator;
import generic_simulator.model.StopEvent;
import supermarket_simulator.model.CloseEvent;
import supermarket_simulator.model.StartEvent;
import supermarket_simulator.model.SupermarketState;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class Optimize {
	
	
	private static final int MIN_CONSECUTIVE_STABLE_RUNS = 100;
	/**
	 * 
	 * Prints the parameters and results
	 * Different parameters test different methods which the test-program contains
	 */

	public static void main(String[] args) {
		System.out.printf(
				"PARAMETRAR\r\n" 
						+ "==========\r\n" 
						+ "Max som ryms, M..........: %d \r\n"
						+ "Ankomshastighet, lambda..: %.2f \r\n" 
						+ "Plocktider, [P_min..Pmax]: [%.2f..%.2f] \r\n"
						+ "Betaltider, [K_min..Kmax]: [%.2f..%.2f] \r\n" 
						+ "Frö, f...................: %d \r\n\r\n",
				K.M, K.L, K.LOW_COLLECTION_TIME, K.HIGH_COLLECTION_TIME, K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME,
				K.SEED);
		
		System.out.printf("Stängning sker tiden %.1f och stophändelsen sker tiden %.1f. \r\n\r\n", K.END_TIME, K.STOP_TIME);

		//Kör simulationen med lika många kassor som det finns kunder, för att få det optimala antalet missade kunder...
		int optimalMissedCustomers = runSim(K.M, K.M, K.L, K.LOW_COLLECTION_TIME, K.HIGH_COLLECTION_TIME,
				K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.END_TIME, K.STOP_TIME, K.SEED).missedCustomers();

		//Kollar ifall vi vill köra metod 2
		if (args[0].equals("2")) {
			int optimalCheckouts = Optimize.optimalCheckouts(K.M, K.L, K.LOW_COLLECTION_TIME, K.HIGH_COLLECTION_TIME,
					K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.END_TIME, K.STOP_TIME, K.SEED);
			
		//Printar ut resultatet
			System.out.printf("Minsta antal kassor som ger minimalt antal missade (%d): %d", optimalMissedCustomers,
					optimalCheckouts);
			
		//Ifall argument = 3 kör metod 3	
		} else if (args[0].equals("3")) {
			int maxMinCheckouts = Optimize.highestMinimumCheckouts(K.M, K.L, K.LOW_COLLECTION_TIME,
					K.HIGH_COLLECTION_TIME, K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.END_TIME, K.STOP_TIME, K.SEED);
			
			System.out.printf("Största minsta antal kassor som ger minimalt antal missade (%d): %d",
					optimalMissedCustomers, maxMinCheckouts);
		}
	}
/**
 * 
 * @param checkouts number of available checkouts 
 * @param customerCapacity maximal amounts of customers in the store 
 * @param arivalFrequency maximal amounts of customers in the store 
 * @param minPickTime smallest pick time available
 * @param maxPickTime highest pick time available
 * @param minPayTime smallest pay time available
 * @param maxPayTime highest pay time available
 * @param closeTime time when store closes
 * @param stopTime time when simulation stops
 * @param seed long randomized number that gives a output based on the value 
 * @return state
 */
	// Metod 1
	public static SupermarketState runSim(int checkouts, int customerCapacity, double arivalFrequency,
			double minPickTime, double maxPickTime, double minPayTime, double maxPayTime, double closeTime,
			double stopTime, long seed) {

		SupermarketState state = new SupermarketState(checkouts, customerCapacity, arivalFrequency, minPickTime,
				maxPickTime, minPayTime, maxPayTime, seed);

		EventQueue eventQueue = new EventQueue(); // Skapar ny instans av klassen EventQueue
		eventQueue.enqueue(new StartEvent(0));
		eventQueue.enqueue(new CloseEvent(closeTime));
		eventQueue.enqueue(new StopEvent(stopTime));
		Simulator sim = new Simulator(state, eventQueue); // Skickar med det state som simuleringen skall utföras på och
															// eventQueue (kön) som den skall innehålla
		sim.run();
		return state;
	}
	/**
	 * Finds the smallest amount of checkouts for the biggest amount of customers
	 * @param customerCapacity number of available checkouts
	 * @param arivalFrequency maximal amounts of customers in the store 
	 * @param minPickTime smallest pick time available
     * @param maxPickTime highest pick time available
     * @param minPayTime smallest pay time available
     * @param maxPayTime highest pay time available
     * @param closeTime time when store closes
     * @param stopTime time when simulation stops
	 * @param seed long randomized number that gives a output based on the value 
	 * @return checkouts, smallest amount of checkouts for the biggest amount of customers
	 */

	// Metod 2
	public static int optimalCheckouts(int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, double closeTime, double stopTime, long seed) {
		int checkouts = customerCapacity;
		int optimalMissed = runSim(checkouts, customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime,
				maxPayTime, closeTime, stopTime, seed).missedCustomers(); // vi kommer missa optimalt antal kunder när
																			// N=M

		int step = checkouts - 1; // optimala antalet kassor finns i intervallet [1, customerCapacity]
		int currentMissed;
		
		while (step > 1) { //Gör en binär sökning för att hitta minsta antalet kassor, som ger optimalt antal missade kunder...
			step /= 2;
			checkouts -= step;
			currentMissed = runSim(checkouts, customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime,
					maxPayTime, closeTime, stopTime, seed).missedCustomers();
			if (currentMissed > optimalMissed) {
				checkouts += step;
			}
		}
		return checkouts;
	}
/**
 * Finds the highest value that optimalCheckouts returns, with randomized seeds each time
 * @param customerCapacity number of available checkouts
 * @param arivalFrequency maximal amounts of customers in the store 
 * @param minPickTime smallest pick time available
 * @param maxPickTime highest pick time available
 * @param minPayTime smallest pay time available
 * @param maxPayTime highest pay time available
 * @param closeTime time when store closes
 * @param stopTime time when simulation stops
 * @param f variable for randomized seed
 * @return highestMin, highest value that optimalCheckouts gets
 */
	// Metod 3
	public static int highestMinimumCheckouts(int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, double closeTime, double stopTime, int f) {

		Random random = new Random(f);
		int highestMin = Integer.MIN_VALUE; // Initial highest minimum value
		int consecutiveStableRuns = 0;

		while (consecutiveStableRuns < MIN_CONSECUTIVE_STABLE_RUNS) {
			int min = optimalCheckouts(customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime,
					maxPayTime, closeTime, stopTime, random.nextLong()); // Run methodtwo med olika seed
			if (min > highestMin) { // Check if new highest minimum found
				highestMin = min;
				consecutiveStableRuns = 0;
			} else {
				consecutiveStableRuns++;
			}
		}
		
		

		return highestMin;
	}

}
