package generic_simulator.model;

import java.util.Observable;
/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach 
 */
@SuppressWarnings("deprecation")
public class State extends Observable {
 	protected double time;
 	private boolean isRunning; // The stop flag
	
	public State() {
		time = 0;
		isRunning = true;
	}
	
	public double time() {
		return time;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	protected void stop() {
		isRunning = false;
	}
}
