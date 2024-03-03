package supermarket_simulator.model;

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

	public SupermarketState(int openCheckouts, int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, long seed) {

		if (openCheckouts < 1) {
			throw new IllegalArgumentException("openCheckouts must be > 0");
		} else if (customerCapacity < 1) {
			throw new IllegalArgumentException("customerCapacity must be > 0");
		} else if (arivalFrequency <= 0) {
			throw new IllegalArgumentException("arivalFrequency must be > 0");
		} else if (minPickTime <= 0) {
			throw new IllegalArgumentException("minPickTime must be > 0");
		} else if (maxPickTime <= 0) {
			throw new IllegalArgumentException("maxPickTime must be > 0");
		} else if (minPayTime <= 0) {
			throw new IllegalArgumentException("minPayTime must be > 0");
		} else if (maxPayTime <= 0) {
			throw new IllegalArgumentException("maxPayTime must be > 0");
		}
		
		this.openCheckouts = openCheckouts;
		this.customerCapacity = customerCapacity;
		this.arivalTimeProvider = new ExponentialTimeProvider(this, arivalFrequency, seed);
		this.pickTimeProvider = new UniformTimeProvider(this, minPickTime, maxPickTime, seed);
		this.payTimeProvider = new UniformTimeProvider(this, minPayTime, maxPayTime, seed);

		this.isClosed = false;
		this.idleCheckouts = openCheckouts;
		this.customers = 0;
		this.payingCustomers = 0;
		this.missedCustomers = 0;
		this.queuedCustomers = 0;
		this.idleCheckoutsTime = 0;
		this.queueingTime = 0;

		this.customerFactory = new CustomerFactory();
		this.checkoutQueue = new FIFO<Customer>();

		setChanged();
	}

	public boolean isClosed() {
		return isClosed;
	}

	protected void close() {
		isClosed = true;
		setChanged();
	}

	public boolean isAtCapacity() {
		return customerCapacity <= customers;
	}

	public int customers() {
		return customers;
	}

	protected void incrementCustomers() throws IllegalStateException {
		if (isAtCapacity()) {
			throw new IllegalStateException("supermarket already at capacity");
		} else {
			customers++;
			setChanged();			
		}

	}

	protected void decrementCustomers() throws IllegalStateException {
		customers--;
		setChanged();
	}

	public int idleCheckouts() {
		return idleCheckouts;
	}

	protected void incrementIdleCheckouts() throws IllegalStateException {
		// antalet lediga kassor ska inte få vara fler än totala antalet kassor
		if (idleCheckouts == openCheckouts) {
			throw new IllegalStateException("max number of checkouts are already idle");
		} else {
			idleCheckouts++;
			setChanged();			
		}

	}

	protected void decrementIdleCheckouts() {
		// antalet lediga kassor ska inte få vara mindre än noll
		if (idleCheckouts == 0) {
			throw new IllegalStateException("number of idle checkouts already zero");
		} else {
			idleCheckouts--;
			setChanged();			
		}

	}

	protected void incrementMissedCustomers() {
		missedCustomers++;
		setChanged();
	}

	public int missedCustomers() {
		return missedCustomers;
	}

	protected void enqueueCustomer(Customer customer) {
		checkoutQueue.enqueue(customer);
		queuedCustomers++; // Antalet kunder som har köat ökar med ett
		setChanged();
	}

	protected Customer dequeueCustomer() {
		setChanged();
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
	
	protected void incrementIdleCheckoutsTime(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("amount must be positive");
		} else {
			idleCheckoutsTime += amount;
			setChanged();
		}
	}

	public double queueingTime() {
		return queueingTime;
	}

	protected void incrementQueueingTime(double amount) throws IllegalArgumentException {
		if (amount < 0) {
			throw new IllegalArgumentException("amount must be positive");
		} else {
			queueingTime += amount;
			setChanged();
		}
	}

	public int payingCustomers() {
		return payingCustomers;
	}

	protected void incrementPayingCustomers() {
		payingCustomers++;
		setChanged();
	}

	public String queueToString() {
		return checkoutQueue.toString();
	}
	
	protected Customer newCustomer() {
		setChanged();
		return customerFactory.getCustomer();
	}

	protected double nextArivalTime() {
		setChanged();
		return arivalTimeProvider.next();
	}

	protected double nextPickTime() {
		setChanged();
		return pickTimeProvider.next();
	}

	protected double nextPayTime() {
		setChanged();
		return payTimeProvider.next();
	}
}
