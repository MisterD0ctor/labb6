package supermarket_simulator.model;

import fifo.FIFO;
import generic_simulator.model.State;
import supermarket_simulator.customers.Customer;
import supermarket_simulator.customers.CustomerFactory;
import supermarket_simulator.time.*;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
@SuppressWarnings("deprecation")
public class SupermarketState extends State {

	private boolean isOpen; // är snabbköpet öppet
	private boolean isClosing; // Det ska vara sant vid ett eventet efter stänging och gör att sista
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
		this.isClosing = false;
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
	
	public int checkoutCount() { //För att man ska kunna se värdet
		return checkoutCount;
	}

	public boolean isOpen() { //För att man ska kunna se värdet
		return isOpen;
	}

	protected void beginClosing() { // starta stängnings-processen
		isOpen = false;
		isClosing = true;
		setChanged(); //För att SupermarketState är Observable av SuperMarketView
	}

	protected boolean isClosing() { //Alla klasser i Supermarket_Simulator.model kan komma åt funktionen
		return isClosing;
	}

	protected void setClosed() { // kallas när starta stängnings-processen är klar
		this.isClosing = false;
		setChanged();
	}

	public boolean isAtCapacity() {
		return customerCapacity <= customerCount;
	}

	public int customerCount() {
		return customerCount;
	}
	
	/**
	 * 
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
	 * 
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

	public int idleCheckouts() {
		return idleCheckouts;
	}

	/**
	 * 
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
	 * 
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

	public int visits() {
		return visits;
	}

	protected void incrementVisits() { //Ökar antalet kunder som betalat med 1
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
		setChanged(); // För att de nya uppdateringarna ska märkas
	}

	protected void enqueueCustomer(Customer customer) { // lägg till en kund i kassakön
		checkoutQueue.enqueue(customer);
		queuedCustomers++; // Antalet kunder som har köat ökar med ett
		setChanged();
	}

	protected Customer dequeueCustomer() { // Hämtar och tar bort första kunden i kassakön
		setChanged();
		return checkoutQueue.dequeue();
	}

	public int queuedCustomers() { //Antalet kunder som har stått i kö
		return queuedCustomers;
	}

	public int queueingCustomers() { //Antalet kunder som köar
		return checkoutQueue.size();
	}

	public double idleCheckoutTime() {
		return idleCheckoutTime;
	}

	/**
	 * 
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

	public double queueingTime() {
		return queueingTime;
	}

	/**
	 * 
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
	
	public double lastCheckoutTime() {
		return this.lastCheckoutTime;
	}
	
	protected void setLastCheckoutTime(double time) {
		this.lastCheckoutTime = time;
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
		return arivalTimeProvider.next(); // Genererar vilken tid nästa ArrivalEvent skall ske
	}

	protected double nextPickTime() {
		setChanged();
		return pickTimeProvider.next(); // Genererar vilken tid nästa PickEvent skall ske
	}

	protected double nextPayTime() { // Genererar vilken tid nästa PayEvent skall ske
		setChanged();
		return payTimeProvider.next();
	}
}
