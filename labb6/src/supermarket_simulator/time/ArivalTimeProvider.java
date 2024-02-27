package supermarket_simulator.time;
import supermarket_simulator.StoreState;
import random.ExponentialRandomStream;

public class ArivalTimeProvider {
	
	private ExponentialRandomStream stream;
	private StoreState state;
	
	public ArivalTimeProvider(StoreState state, double lambda, long seed) {
		stream = new ExponentialRandomStream(lambda, seed);
		this.state = state;
	}
	
	public double next() {
		return state.time + stream.next();
	}
}
