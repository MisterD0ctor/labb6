package supermarket_simulator;
import generic_simulator.State;

class StoreState extends State {
	public boolean isOpen;
	
	final public int cashierCount;
	final public int maxCustomerCount;
	
	public StoreState(int n, int m, double lambda, double kMin, double kMax, double pMin, double pMax, int f) {
		cashierCount = n;
		maxCustomerCount = m;
	}
}
