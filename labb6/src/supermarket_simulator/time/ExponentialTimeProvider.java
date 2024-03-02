package supermarket_simulator.time;

import supermarket_simulator.SupermarketState;
import random.ExponentialRandomStream;

public class ExponentialTimeProvider {

	private ExponentialRandomStream stream;
	private SupermarketState state;

	public ExponentialTimeProvider(SupermarketState state, double lambda, long seed) {
		stream = new ExponentialRandomStream(lambda, seed);
		this.state = state;
	}

	public double next() {
		return state.time + stream.next();
	}
}
