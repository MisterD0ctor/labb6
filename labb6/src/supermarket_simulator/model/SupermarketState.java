package supermarket_simulator.model;

import fifo.FIFO;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;
import supermarket_simulator.customers.CustomerFactory;
import supermarket_simulator.time.*;

/**
 * The state of a supermarket
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
@SuppressWarnings("deprecation")
public class SupermarketState extends State {

	private boolean isOpen; // är snabbköpet öppet
								// beräkningen av kötid samt ledig-kassatid sker.

	private final int checkoutCount; // antal öppna kassor
	private final int customerCapacity; // maximalt antal kunder som får plats i snabbköpet

	private int idleCheckouts; // antal lediga kassor
	private int customerCount; // antal kunder i snabbköpet
	private int visits; // antal kunder som besökt snabbköpet och betalat
	private int attemptedVisits; // antal kunder som försökt besökt snabbköpet
	private int missedCustomers; // antalt missade kunder
	private int queuedCustomers; // antlal kunder som köat totalt
	private double idleCheckoutTime; // totala tiden som kassor stått lediga
	private double queueingTime; // totala tiden som kunder stått i kassakön
	private double lastCheckoutTime;
	
	private final CustomerFactory customerFactory; // skapar nya kunder med unika id
	private final FIFO<Customer> checkoutQueue; // kassakön

	private final ExponentialTimeProvider arivalTimeProvider; // genererar tider för ankomstevent
	private final UniformTimeProvider pickTimeProvider; // genererar tider för plockevent
	private final UniformTimeProvider payTimeProvider; // genererar tider för betalningsevent

	/**
     * Creates a new supermarket state
     * @param openCheckouts The number of open checkouts
     * @param customerCapacity The maximal number of customers allowd in the supermarket
     * @param arivalFrequency The average frequency of costumer arrivals
     * @param minPickTime The shortest time a customer can take to pick their products
     * @param maxPickTime The longest time a customer can take to pick their products
     * @param minPayTime The shortest time a customer can take to pay for their products
     * @param maxPayTime The longest time a customer can take to pay for their products
     * @param seed The seed used for random number generation
     */
	public SupermarketState(int checkoutCount, int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, long seed) {

		// Kontrollera att alla parametrar är giltiga
		if (checkoutCount <= 0) {
			throw new IllegalArgumentException("openCheckouts must be > 0");
		} else if (customerCapacity <= 0) {
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

		this.checkoutCount = checkoutCount; //Tar variabeln från parametern och sparar den i tillståndsvariabeln, Den mörkblåa försvinner efter konstruktorn.
		this.customerCapacity = customerCapacity;
		this.arivalTimeProvider = new ExponentialTimeProvider(this, arivalFrequency, seed);
		this.pickTimeProvider = new UniformTimeProvider(this, minPickTime, maxPickTime, seed);
		this.payTimeProvider = new UniformTimeProvider(this, minPayTime, maxPayTime, seed);

		this.isOpen = true; //Annars får vi inga nya arrivals
		this.idleCheckouts = checkoutCount;
		this.customerCount = 0;
		this.visits = 0;
		this.attemptedVisits = 0;
		this.missedCustomers = 0;
		this.queuedCustomers = 0;
		this.idleCheckoutTime = 0;
		this.queueingTime = 0;
		this.lastCheckoutTime = 0;

		this.customerFactory = new CustomerFactory();
		this.checkoutQueue = new FIFO<Customer>();

		setChanged();
	}

	@Override
	protected void setTime(double time) { // synligör setTime metoden för alla event
		super.setTime(time);
	}
	
	/**
	 * @return The number of open checkouts
	 */
	public int checkoutCount() { //För att man ska kunna se värdet
		return checkoutCount;
	}

	/**
	 * @return true if the supermaket is open
	 */
	public boolean isOpen() { //För att man ska kunna se värdet
		return isOpen;
	}

	/**
	 * Sets if the supermarket is open or not
	 * @param value If the supermarket should be open or not
	 */
	protected void setIsOpen(boolean value) { // starta stängnings-processen
		isOpen = false;
		setChanged(); //För att SupermarketState är Observable av SuperMarketView
	}

	/**
	 * 
	 * @return true if the maximum number of customers are inside the supermarket
	 */
	public boolean isAtCapacity() {
		return customerCapacity <= customerCount;
	}

	/**
	 * 
	 * @return The number of customers that are inside the supermarket
	 */
	public int customerCount() {
		return customerCount;
	}
	
	/**
	 * Increments the number of customers in the supermarket by one
	 * @throws IllegalStateException When the supermarket is at max capacity
	 */
	protected void incrementCustomerCount() throws IllegalStateException { // ökar antalet kunder i snabbköpet med ett
		if (isAtCapacity()) {
			throw new IllegalStateException("supermarket already at capacity");
		} else {
			customerCount++;
			setChanged();
		}

	}

	/**
	 * Decrements the number of customers in the supermarket by one
	 * @throws IllegalStateException When the amount of costumers is zero or less
	 */
	protected void decrementCustomerCount() throws IllegalStateException {
		if (customerCount <= 0) {
			throw new IllegalStateException("supermarket already empty");
		} else {
			customerCount--;
			setChanged();
		}
	}

	/**
	 * 
	 * @return The number of checkouts that are currently idle
	 */
	public int idleCheckouts() {
		return idleCheckouts;
	}

	/**
	 * Increments the number of idle checkouts by one
	 * @throws IllegalStateException When the all the checkouts are already idle
	 */
	protected void incrementIdleCheckouts() throws IllegalStateException { // när en kund lämnar till en kassa
		// antalet lediga kassor ska inte få vara fler än totala antalet kassor
		if (idleCheckouts == checkoutCount) {
			throw new IllegalStateException("max number of checkouts are already idle");
		} else {
			idleCheckouts++;
			setChanged();
		}

	}

	/**
	 * Decrements the number of idle checkouts by one
	 * @throws IllegalStateException When all the checkouts are already in use
	 */
	protected void decrementIdleCheckouts() throws IllegalStateException { // när en kund går till en kassa
		// antalet lediga kassor ska inte få vara mindre än noll
		if (idleCheckouts == 0) {
			throw new IllegalStateException("number of idle checkouts already zero");
		} else {
			idleCheckouts--;
			setChanged();
		}

	}

	/**
	 * 
	 * @return The number of customers that has visited supermarket and paid for their products
	 */
	public int visits() {
		return visits;
	}

	/**
	 * Increment the number of customers that has visited supermarket and paid for their products by one
	 */
	protected void incrementVisits() { // Ökar antalet kunder som betalat med 1
		visits++;
		setChanged();
	}

	/**
	 * 
	 * @return The number of customers that has atemted to enter and visit the supermarket
	 */
	public int attemptedVisits() {
		return attemptedVisits;
	}

	/**
	 * Increments the number of attempted visits by one
	 */
	protected void incrementAttemptedVisits() {
		attemptedVisits++;
		setChanged();
	}

	/**
	 * 
	 * @return The number of customers that have been missed
	 */
	public int missedCustomers() {
		return missedCustomers;
	}

	/**
	 * Increments the number of missed customers by one
	 */
	protected void incrementMissedCustomers() {
		missedCustomers++;
		setChanged(); // För att de nya uppdateringarna ska märkas
	}

	/**
	 * Adds a customer to the checkout queue
	 * @param customer The customer to be added to the checkout queue
	 */
	protected void enqueueCustomer(Customer customer) { // lägg till en kund i kassakön
		checkoutQueue.enqueue(customer);
		queuedCustomers++; // Antalet kunder som har köat ökar med ett
		setChanged();
	}

	/**
	 * Gets and removes the first customer in the checkout queue
	 * @return The first customer in the queue
	 */
	protected Customer dequeueCustomer() { // Hämtar och tar bort första kunden i kassakön
		setChanged();
		return checkoutQueue.dequeue();
	}

	/**
	 * 
	 * @return The number of customers that have been in the chekout queue
	 */
	public int queuedCustomers() { //Antalet kunder som har stått i kö
		return queuedCustomers;
	}

	/**
	 * 
	 * @return The number of customers currently in the chekout queue
	 */
	public int queueingCustomers() { //Antalet kunder som köar
		return checkoutQueue.size();
	}

	public double idleCheckoutTime() {
		return idleCheckoutTime;
	}

	/**
	 * Increments the amount of time that checkouts have been idle
	 * @param amount Amount of time to increment by
	 * @throws IllegalArgumentException When amount is negative
	 */
	protected void incrementIdleCheckoutTime(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("amount must be positive");
		} else {
			idleCheckoutTime += amount;
			setChanged();
		}
	}

	/**
	 * 
	 * @return The total amount of time that customers have stood in the chekout queue
	 */
	public double queueingTime() {
		return queueingTime;
	}

	/**
	 * Increments the amount of time that customers have stood in the checkout queue
	 * @param amount Amount of time to increment by
	 * @throws IllegalArgumentException When amount is negative
	 */
	protected void incrementQueueingTime(double amount) throws IllegalArgumentException {
		if (amount < 0) {
			throw new IllegalArgumentException("amount must be positive");
		} else {
			queueingTime += amount;
			setChanged();
		}
	}
	
	/**
	 * 
	 * @return The time of the last customer checkout
	 */
	public double lastCheckoutTime() {
		return this.lastCheckoutTime;
	}
	
	/**
	 * Sets the time of the last customer checkout
	 * @param time The time 
	 */
	protected void setLastCheckoutTime(double time) {
		this.lastCheckoutTime = time;
		setChanged();
	}

	/**
	 * 
	 * @return A string representation of the checkout queue
	 */
	public String queueToString() {
		return checkoutQueue.toString();
	}

	/**
	 * 
	 * @return A new customer with a unique id
	 */
	protected Customer newCustomer() {
		setChanged();
		return customerFactory.getCustomer();
	}

	/**
	 * 
	 * @return The time of the next arival event
	 */
	protected double nextArivalTime() {
		setChanged();
		return arivalTimeProvider.next(); // Genererar vilken tid nästa ArrivalEvent skall ske
	}

	/**
	 * 
	 * @return The time of the next pick event
	 */
	protected double nextPickTime() {
		setChanged();
		return pickTimeProvider.next(); // Genererar vilken tid nästa PickEvent skall ske
	}

	/**
	 * 
	 * @return The time of the next pay event
	 */
	protected double nextPayTime() { // Genererar vilken tid nästa PayEvent skall ske
		setChanged();
		return payTimeProvider.next();
	}
}
