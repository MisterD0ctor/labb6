package generic_simulator;

public class Simulator {
	private State state; //Tillståndet som simuleringen körs på
	private EventQueue eventQueue; //Event kön som innehåller alla event
	
	public Simulator(State state, EventQueue eventQueue) {
		this.state = state; // För att hålla koll på vilket state det är...
		this.eventQueue = eventQueue; // För att vi vill kunna välja vilka event som ska finnas i eventkön från början
	}
	
	public void run() { 
		while (state.running) {
			Event event = eventQueue.dequeue(); //plockar event från eventqueue via dequeue för att sedan köra dem
			
			if (event != null) {
				event.execute(state, eventQueue); //Ifall inte null, körs eventet				
			}
		}
	}
}
