package random;

import java.util.Random;
/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach 
 */
public class ExponentialRandomStream {
	
	private Random rand;
	private double lambda;
	  
	public ExponentialRandomStream(double lambda, long seed) {
	  	rand = new Random(seed);
	  	this.lambda = lambda;
	}
	  
	public ExponentialRandomStream(double lambda) {
		rand = new Random();
	    this.lambda = lambda;
	}
	  
	public double next() {
	  	return -Math.log(rand.nextDouble()) / lambda;
	}
}

