package supermarket_simulator.time;

import random.ExponentialRandomStream;
import supermarket_simulator.model.SupermarketState;

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
