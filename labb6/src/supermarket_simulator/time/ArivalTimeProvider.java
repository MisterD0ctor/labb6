package supermarket_simulator.time;
import supermarket_simulator.StoreState;
import random.ExponentialRandomStream;

public class ArivalTimeProvider {
	
	private ExponentialRandomStream stream;
	
	public ArivalTimeProvider(StoreState state, double lambda, long seed) {
		stream = new ExponentialRandomStream(lambda, seed);
	}
	
	public double next() {
		return stream.next();
	}
}
