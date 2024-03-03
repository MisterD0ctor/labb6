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

	public static void main(String[] args) {
		if (args[0] == "test") {
			System.out.print(Optimize.optimalCheckouts(K.M, K.L, K.LOW_COLLECTION_TIME, K.HIGH_COLLECTION_TIME, K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.END_TIME, K.STOP_TIME, K.SEED));			
		}
		System.out.print(Optimize.highestMin(K.M, K.L, K.LOW_COLLECTION_TIME, K.HIGH_COLLECTION_TIME, K.LOW_PAYMENT_TIME, K.HIGH_PAYMENT_TIME, K.END_TIME, K.STOP_TIME, K.SEED)); // vilka parametrar?????????
	}

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

	public static int optimalCheckouts(int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, double closeTime, double stopTime, long seed) {
		int checkouts = customerCapacity;
		int optimalMissed = runSim(checkouts, customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime,
				maxPayTime, closeTime, stopTime, seed).missedCustomers();

		int step = checkouts - 1; // optimala antalet kassor i intervallet [1, customerCapacity]
		int currentMissed;

		while (step > 1) {
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

	// Metod 3 - ska starta highestMin.....

	public static int highestMin(int customerCapacity, double arivalFrequency, double minPickTime, double maxPickTime,
			double minPayTime, double maxPayTime, double closeTime, double stopTime, int f) {

		Random random = new Random(f);
		int highestMin = Integer.MIN_VALUE; // Initial highest minimum value

		for (int i = 0; i < 100; i++) { // Loop until 100 consecutive stable iterations
			int min = optimalCheckouts(customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime,
					maxPayTime, closeTime, stopTime, random.nextLong()); // Run methodtwo
			if (min > highestMin) { // Check if new highest minimum found
				highestMin = min;
			}
		}

		return highestMin;
	}

}
