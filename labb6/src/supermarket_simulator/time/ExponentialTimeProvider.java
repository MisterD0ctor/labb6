package supermarket_simulator.time;

import random.ExponentialRandomStream;
import generic_simulator.model.State;

/**
 * Provides new random points in time that events should happen
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class ExponentialTimeProvider {

	private ExponentialRandomStream stream;
	private State state;

	/**
	 * @param state The state used as a referece point for future times
	 * @param lambda The avrage frequecy of new times
	 * @param seed The seed used for generation of random times
	 */
	public ExponentialTimeProvider(State state, double lambda, long seed) {
		stream = new ExponentialRandomStream(lambda, seed); //Sparar ner frekvens och frö
		this.state = state; // För att vi ska kunna använda state.time 
	}

	/**
	 * @return A new random point in time
	 */
	public double next() {
		return state.time() + stream.next();
	}
}
