package supermarket_simulator;
import generic_simulator.State;
import supermarket_simulator.time.*;

public class StoreState extends State {
	public boolean isOpen;
	
	public final int cashierCount;
	public final int maxCustomerCount;
	
	public final ArivalTimeProvider arivalTime;
	
	public StoreState(int n, int m, double lambda, 
			double kMin, double kMax, double pMin, 
			double pMax, long f) {
		cashierCount = n;
		maxCustomerCount = m;
		arivalTime = new ArivalTimeProvider(this, lambda, f);
	}
}
