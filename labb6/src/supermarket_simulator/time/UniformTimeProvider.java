package supermarket_simulator.time;

import random.UniformRandomStream;
import generic_simulator.model.State;

/**
 * Provides uniform random points in time
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class UniformTimeProvider {

	private UniformRandomStream stream;
	private State state;

	/**
	 * 
	 * @param state The state used as a referece point for future times
	 * @param minTime Minimum time delta for new times
	 * @param maxTime Maximum time delta for new times
	 * @param seed The seed used for generation of random times
	 */
	public UniformTimeProvider(State state, double minTime, double maxTime, long seed) {
		stream = new UniformRandomStream(minTime, maxTime, seed);
		this.state = state;
	}

	/**
	 * @return A new random point in time
	 */
	public double next() {
		return state.time() + stream.next();
	}
}
