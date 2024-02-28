package supermarket_simulator.events;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;

class PickEvent extends Event {

	public PickEvent(double time) {
		super(time);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		/* TODO
		 * Denna händelsesort motsvarar att en kund C plockat alla sina varor, gått bort till
		 * kassorna och är klar att betala.
		 * 
		 * Om det finns lediga kassor så går C omedelbart till en av dem och scanning/betalning
         * inleds. Antalet lediga kassor minskar med 1 och vi genererar en framtida
		 * betalningshändelse för C som läggs till händelsekön. Om alla kassor däremot är
		 * upptagna ställs C istället i kassakö. Det finns endast en gemensam kassakö för alla
		 * kassor (inte en per kassa).
		 */
	}
	
}
