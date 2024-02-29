package supermarket_simulator;

import java.util.ArrayDeque;

import generic_simulator.State;
import supermarket_simulator.time.*;

public class StoreState extends State {
	public boolean isClosed;
	
	public final int checkoutsCount;
	public final int maxCustomerCount;
	
	public int availableCheckoutsCount;
	public int customerCount;
	public int payCount;
	public int missedCustomerCount;
	public double checkoutsInactivityTime;
	public double totalQueueTime;
	
	public final CustomerFactory customerFactory;
	public ArrayDeque<Customer> checkoutQueue;
	
	public final ExponentialTimeProvider arivalTimeProvider;
	public final UniformTimeProvider pickTimeProvider;
	public final UniformTimeProvider payTimeProvider;
	
	public StoreState(int n, int m, double lambda, 
			double kMin, double kMax, double pMin, 
			double pMax, long f) {
		
		checkoutsCount = n;
		maxCustomerCount = m;
		arivalTimeProvider = new ExponentialTimeProvider(this, lambda, f);
		pickTimeProvider = new UniformTimeProvider(this, kMin, kMax, f);
		payTimeProvider = new UniformTimeProvider(this, pMin, pMax, f);
		
		isClosed = false;
		availableCheckoutsCount = n;
		customerCount = 0;
		payCount = 0;
		missedCustomerCount = 0;
		checkoutsInactivityTime = 0;
		totalQueueTime = 0;
		
		customerFactory = new CustomerFactory();
		checkoutQueue = new ArrayDeque<Customer>();
	}
}
