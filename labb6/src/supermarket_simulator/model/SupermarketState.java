package supermarket_simulator.model;

import fifo.FIFO;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;
import supermarket_simulator.customers.CustomerFactory;
import supermarket_simulator.time.*;

@SuppressWarnings("deprecation")
public class SupermarketState extends State {

	private boolean isClosed;

	private final int checkouts; // antal öppna kassor
	private final int customerCapacity; // maximalt antal kunder som får plats i snabbköpet

	private int idleCheckouts; // antal lediga kassor
	private int customers; // antal kunder i snabbköpet
	private int visits; // antal kunder som besökt snabbköpet och betalat
	private int attemptedVisits; // antal kunder som försökt besökt snabbköpet
	private int missedCustomers; // antalt missade kunder
	private int queuedCustomers; // antlal kunder som köat
	private double idleCheckoutTime; // totala tiden som kassor stått lediga
	private double queueingTime; // totala tiden som kunder stått i kassakön

	private final CustomerFactory customerFactory; // skapar nya kunder med unika id
	private final FIFO<Customer> checkoutQueue; // kassakön

	private final ExponentialTimeProvider arivalTimeProvider;
	private final UniformTimeProvider pickTimeProvider;
	private final UniformTimeProvider payTimeProvider;

	public SupermarketState(int checkouts, int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, long seed) {

		if (checkouts < 1) {
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
		
		this.checkouts = checkouts;
		this.customerCapacity = customerCapacity;
		this.arivalTimeProvider = new ExponentialTimeProvider(this, arivalFrequency, seed);
		this.pickTimeProvider = new UniformTimeProvider(this, minPickTime, maxPickTime, seed);
		this.payTimeProvider = new UniformTimeProvider(this, minPayTime, maxPayTime, seed);

		this.isClosed = false;
		this.idleCheckouts = checkouts;
		this.customers = 0;
		this.visits = 0;
		this.attemptedVisits = 0;
		this.missedCustomers = 0;
		this.queuedCustomers = 0;
		this.idleCheckoutTime = 0;
		this.queueingTime = 0;

		this.customerFactory = new CustomerFactory();
		this.checkoutQueue = new FIFO<Customer>();

		setChanged();
	}

	public int checkouts() {
		return checkouts;
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
		if (customers <= 0) {
			throw new IllegalStateException("supermarket already empty");
		} else {
			customers--;
			setChanged();
		}
	}

	public int idleCheckouts() {
		return idleCheckouts;
	}

	protected void incrementIdleCheckouts() throws IllegalStateException {
		// antalet lediga kassor ska inte få vara fler än totala antalet kassor
		if (idleCheckouts == checkouts) {
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
	
	public int visits() {
		return visits;
	}

	protected void incrementVisits() {
		visits++;
		setChanged();
	}
	
	public int attemptedVisits() {
		return attemptedVisits;
	}

	protected void incrementAttemptedVisits() {
		attemptedVisits++;
		setChanged();
	}

	public int missedCustomers() {
		return missedCustomers;
	}
	
	protected void incrementMissedCustomers() {
		missedCustomers++;
		setChanged();
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

	public double idleCheckoutTime() {
		return idleCheckoutTime;
	}
	
	protected void incrementIdleCheckoutTime(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("amount must be positive");
		} else {
			idleCheckoutTime += amount;
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
