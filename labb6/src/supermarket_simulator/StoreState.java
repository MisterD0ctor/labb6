package supermarket_simulator;
import generic_simulator.State;
import supermarket_simulator.time.*;

public class StoreState extends State {
	public boolean isOpen;
	
	public final int cashierCount;
	public final int maxCustomerCount;
	
	public int availableCashierCount;
	public int customerCount;
	public int payCount;
	public int missedCustomerCount;
	public double cashierInactivityTime;
	public double totalQueueTime;
	
	public final ExponentialTimeProvider arivalTime;
	public final UniformTimeProvider pickTime;
	public final UniformTimeProvider payTime;
	
	public StoreState(int n, int m, double lambda, 
			double kMin, double kMax, double pMin, 
			double pMax, long f) {
		
		cashierCount = n;
		maxCustomerCount = m;
		arivalTime = new ExponentialTimeProvider(this, lambda, f);
		pickTime = new UniformTimeProvider(this, kMin, kMax, f);
		payTime = new UniformTimeProvider(this, pMin, pMax, f);
		
		availableCashierCount = 0;
		customerCount = 0;
		payCount = 0;
		missedCustomerCount = 0;
		cashierInactivityTime = 0;
		totalQueueTime = 0;
	}
}
