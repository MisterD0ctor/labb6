package random;

import java.util.Random;

public class UniformRandomStream { //Används av UniformTimeProvider --> PickEvent och PayEvent

	private Random rand;
	private double lower, width;

	public UniformRandomStream(double lower, double upper, long seed) {
		rand = new Random(seed);
		this.lower = lower; //Lägsta talet som skall genereras
		this.width = upper - lower; //Största talet - lägsta (avståndet)
	}

	public UniformRandomStream(double lower, double upper) { // Används inte
		rand = new Random();
		this.lower = lower;
		this.width = upper - lower;
	}

	public double next() {
		return lower + rand.nextDouble() * width; // Double 64 bit (0->1)
	}
}
