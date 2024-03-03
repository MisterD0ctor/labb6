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
		Optimize op = new Optimize();
		System.out.print(op.optimalCheckouts(5, 1, 0.5, 1.0, 2.0, 3.0, 10, 999, 1234));
		System.out.print(op.highestMin(5, 1, 0.5, 1.0, 2.0, 3.0, 10, 999, 1234)); // vilka parametrar?????????
	}

	public SupermarketState runSim(int checkouts, int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, double closeTime, double stopTime, long seed) { // Tar
																														// in

		SupermarketState state = new SupermarketState(checkouts, customerCapacity, arivalFrequency, minPickTime,
				maxPickTime, minPayTime, maxPayTime, seed);
		//

		EventQueue eventQueue = new EventQueue(); // Skapar ny instans av klassen EventQueue
		eventQueue.enqueue(new StartEvent(0));
		eventQueue.enqueue(new CloseEvent(closeTime));
		eventQueue.enqueue(new StopEvent(stopTime));
		Simulator sim = new Simulator(state, eventQueue); // Skickar med det state som simuleringen skall utföras på och
															// eventQueue (kön) som den skall innehålla
		sim.run();
		return state;
	}

	public int optimalCheckouts(int customerCapacity, double arivalFrequency, double minPickTime, double maxPickTime,
			double minPayTime, double maxPayTime, double closeTime, double stopTime, long seed) {
		int i = customerCapacity;
		int optimalMissed = runSim(i, customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime,
				maxPayTime, closeTime, stopTime, seed).missedCustomers();
		;
		int step = i - 1;
		int currentMissed;

		while (step > 0) {
			step /= 2;
			i -= step;
			currentMissed = runSim(i, customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime,
					maxPayTime, closeTime, stopTime, seed).missedCustomers();
			if (currentMissed > optimalMissed) {
				i += step;
			}
		}
		return i;
	}

	// Metod 3 - ska starta highestMin.....

	public int highestMin(int customerCapacity, double arivalFrequency, double minPickTime, double maxPickTime,
			double minPayTime, double maxPayTime, double closeTime, double stopTime, int f) {

		Random random = new Random(f);
		int highestMin = Integer.MIN_VALUE; // Initial highest minimum value
		int consecutiveStableCount = 0;

		while (consecutiveStableCount < 100) { // Loop until 100 consecutive stable iterations
			int min = optimalCheckouts(customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime,
					maxPayTime, closeTime, stopTime, f); // Run methodtwo
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
