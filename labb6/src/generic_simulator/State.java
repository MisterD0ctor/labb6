package generic_simulator;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class State extends Observable {
 	public float time;
	public boolean running; // The stop flag
	
	public State() {
		time = 0f;
		running = true;
	}
}
