package supermarket_simulator.time;
import supermarket_simulator.StoreState;
import random.ExponentialRandomStream;

public class ExponentialTimeProvider {
	
	private ExponentialRandomStream stream;
	private StoreState state;
	
	public ExponentialTimeProvider(StoreState state, double lambda, long seed) {
		stream = new ExponentialRandomStream(lambda, seed);
		this.state = state;
	}
	
	public double next() {
		return state.time + stream.next();
	}
}
