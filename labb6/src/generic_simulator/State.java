package generic_simulator;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class State extends Observable {
 	public double time;
	public boolean running; // The stop flag
	
	public State() {
		time = 0;
		running = true;
	}
}
