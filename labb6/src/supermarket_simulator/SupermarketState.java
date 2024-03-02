package supermarket_simulator;

import java.util.ArrayDeque;

import fifo.FIFO;
import generic_simulator.State;
import supermarket_simulator.customers.Customer;
import supermarket_simulator.customers.CustomerFactory;
import supermarket_simulator.time.*;

@SuppressWarnings("deprecation")
public class SupermarketState extends State {

	private boolean isClosed;

	private final int openCheckouts; // antal öppna kassor
	private final int customerCapacity; // maximalt antal kunder som får plats i snabbköpet

	private int idleCheckouts; // antal lediga kassor
	private int customers; // antal kunder i snabbköpet
	private int payingCustomers; // antal kunder som betalat
	private int missedCustomers; // antalt missade kunder
	private int queuedCustomers; // antlal kunder som köat
	private double idleCheckoutsTime; // totala tiden som kassor stått lediga
	private double queueingTime; // totala tiden som kunder stått i kassakön

	private final CustomerFactory customerFactory; // skapar nya kunder med unika id
	private final FIFO<Customer> checkoutQueue; // kassakön

	private final ExponentialTimeProvider arivalTimeProvider;
	private final UniformTimeProvider pickTimeProvider;
	private final UniformTimeProvider payTimeProvider;

	public SupermarketState(int n, int m, double lambda, double kMin, double kMax, double pMin, double pMax, long f) {

		openCheckouts = n;
		customerCapacity = m;
		arivalTimeProvider = new ExponentialTimeProvider(this, lambda, f);
		pickTimeProvider = new UniformTimeProvider(this, kMin, kMax, f);
		payTimeProvider = new UniformTimeProvider(this, pMin, pMax, f);

		isClosed = false;
		idleCheckouts = n;
		customers = 0;
		payingCustomers = 0;
		missedCustomers = 0;
		queuedCustomers = 0;
		idleCheckoutsTime = 0;
		queueingTime = 0;

		customerFactory = new CustomerFactory();
		checkoutQueue = new FIFO<Customer>();
		
		setChanged();
	}

	public boolean isClosed() {
		return isClosed;
	}
	
	public void close() {
		isClosed = true;
		setChanged();
	}
	
	public boolean isAtCapacity() {
		return customerCapacity <= customers;
	}
	
	public int gustomers() {
		return customers;
	}
	
	public void incrementCustomers() throws IllegalStateException {
		if (isAtCapacity()) {
			throw new IllegalStateException("supermarket already at capacity");
		}
		
		customers++;
		setChanged();
	}
	
	public void decrementCustomers() throws IllegalStateException {
		customers++;
		setChanged();
	}
	
	public int idleCheckouts() {
		return idleCheckouts;
	}
	
	public void incrementIdleCheckouts() throws IllegalStateException {
		// antalet lediga kassor ska inte få vara fler än totala antalet kassor
		if (idleCheckouts == openCheckouts) {
			throw new IllegalStateException("max number of checkouts are already idle");
		}
		
		idleCheckouts++;
		setChanged();
	}
	
	public void decrementIdleCheckouts() {
		// antalet lediga kassor ska inte få vara mindre än noll
		if (idleCheckouts == 0) {
			throw new IllegalStateException("number of idle checkouts already zero");
		}
		
		idleCheckouts--;
		setChanged();
	}
	
	public void incrementMissedCustomers() {
		missedCustomers++;
		setChanged();
	}
	
	public int missedCustomers() {
		return missedCustomers;
	}
	
	public void enqueueCustomer(Customer customer) {
		checkoutQueue.enqueue(customer);
		queuedCustomers++;
		setChanged();
	}
	
	public Customer dequeueCustomer() {
		return checkoutQueue.dequeue();	
	}
	
	public int queuedCustomers() {
		return queuedCustomers;
	}
	
	public int queueingCustomers() {
		return checkoutQueue.size();
	}
	
	public double idleCheckoutsTime() {
		return idleCheckoutsTime;
	}
	
	public void incrementQueueingTime(double amount) throws IllegalArgumentException {
		if (amount < 0) {
			throw new IllegalArgumentException("amount must be positive");
		} else {
			queueingTime += amount;
		}
	}
	
	public int payingCustomers() {
		return payingCustomers;
	}
	
	public void incrementPayingCustomers() {
		payingCustomers++;
	}
	
	public double queueingTime() {
		return queueingTime;
	}
	
	public Customer getCustomer() {
		return customerFactory.getCustomer();
	}
	
	public double nextArivalTime() {
		return arivalTimeProvider.next();
	}
	
	public double nextPickTime() {
		return pickTimeProvider.next();
	}
	
	public double nextPayTime() {
		return payTimeProvider.next();
	}
}
