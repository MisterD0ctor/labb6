package supermarket_simulator.time;

import random.ExponentialRandomStream;
import generic_simulator.model.State;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class ExponentialTimeProvider {

	private ExponentialRandomStream stream;
	private State state;

	public ExponentialTimeProvider(State state, double lambda, long seed) {
		stream = new ExponentialRandomStream(lambda, seed); //Sparar ner frekvens och frö
		this.state = state; // För att vi ska kunna använda state.time 
	}

	public double next() {
		return state.time() + stream.next();
	}
}
