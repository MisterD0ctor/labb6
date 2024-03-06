 package generic_simulator.model;

import java.util.Observable;

/**
 * A generic state of a simulation
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
@SuppressWarnings("deprecation")
public class State extends Observable {
	private double time;
	private boolean isRunning; // The stop flag

	/*
	 * Creates a new running state at time 0
	 */
	public State() {
		time = 0;
		isRunning = true;
	}

	/**
	 * 
	 * @return The current held time of this state
	 */
	public double time() {
		return time;
	}

	/**
	 * Sets the time of this state
	 * @param time The new time the state should have
	 */
	protected void setTime(double time) {
		this.time = time;
		setChanged();
	}

	/**
	 * 
	 * @return true if the state is beig simulated on
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Stops the simulation
	 */
	protected void stop() {
		isRunning = false;
		setChanged();
	}
}
