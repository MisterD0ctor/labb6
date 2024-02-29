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
	
	public final ExponentialTimeProvider arivalTime;
	public final UniformTimeProvider pickTime;
	public final UniformTimeProvider payTime;
	
	public StoreState(int n, int m, double lambda, 
			double kMin, double kMax, double pMin, 
			double pMax, long f) {
		
		checkoutsCount = n;
		maxCustomerCount = m;
		arivalTime = new ExponentialTimeProvider(this, lambda, f);
		pickTime = new UniformTimeProvider(this, kMin, kMax, f);
		payTime = new UniformTimeProvider(this, pMin, pMax, f);
		
		isClosed = false;
		availableCheckoutsCount = 0;
		customerCount = 0;
		payCount = 0;
		missedCustomerCount = 0;
		checkoutsInactivityTime = 0;
		totalQueueTime = 0;
		
		customerFactory = new CustomerFactory();
		checkoutQueue = new ArrayDeque<Customer>();
	}
}
