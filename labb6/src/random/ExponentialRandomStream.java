package random;

import java.util.Random;

public class ExponentialRandomStream {

	private Random rand;
	private double lambda;

	public ExponentialRandomStream(double lambda, long seed) {
		rand = new Random(seed); //Skapar en slumptalsgenerator av ett frö
		this.lambda = lambda; //håller koll på frekvensen av ny slumptal
	}

	public ExponentialRandomStream(double lambda) { //andra parametrar
		rand = new Random();
		this.lambda = lambda;
	}

	public double next() {
		return -Math.log(rand.nextDouble()) / lambda; //rand.nextdouble generar ett tal från 0->1 (flyttal)
	}
}
