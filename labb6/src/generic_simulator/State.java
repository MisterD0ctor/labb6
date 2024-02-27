package generic_simulator;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class State extends Observable {
	private int time; // float type? Time class?
	private boolean run; // The stop flag
	
	public State() {
		time = 0;
		run = false;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
}
