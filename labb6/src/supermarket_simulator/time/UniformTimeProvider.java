package supermarket_simulator.time;

import random.UniformRandomStream;
import supermarket_simulator.model.SupermarketState;

public class UniformTimeProvider {

	private UniformRandomStream stream;
	private SupermarketState state;

	public UniformTimeProvider(SupermarketState state, double minTime, double maxTime, long seed) {
		stream = new UniformRandomStream(minTime, maxTime, seed);
		this.state = state;
	}

	public double next() {
		return state.time() + stream.next();
	}
}
