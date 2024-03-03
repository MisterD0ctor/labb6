package supermarket_simulator.time;

import random.UniformRandomStream;
import generic_simulator.model.State;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class UniformTimeProvider {

	private UniformRandomStream stream;
	private State state;

	public UniformTimeProvider(State state, double minTime, double maxTime, long seed) {
		stream = new UniformRandomStream(minTime, maxTime, seed);
		this.state = state;
	}

	public double next() {
		return state.time() + stream.next();
	}
}
