package supermarket_simulator.time;
import random.UniformRandomStream;
import supermarket_simulator.StoreState;

public class UniformTimeProvider {
	
	private UniformRandomStream stream;
	private StoreState state;
	
	public UniformTimeProvider(StoreState state, double minTime, double maxTime, long seed) {
		stream = new UniformRandomStream(minTime, maxTime, seed);
		this.state = state;
	}
	
	public double next() {
		return state.time + stream.next();
	}
}
