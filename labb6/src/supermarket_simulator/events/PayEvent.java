package supermarket_simulator.events;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;

public class PayEvent extends Event {
	
	public PayEvent(double time) {
		super(time);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		/* 
		 * När detta inträffar minskar antalet kunder i snabbköpet med 1 och vi kan anteckna att
		 * ytterligare en kund har handlat. Samtidigt som kunden går blir en kassa ledig.
		 * 
		 * Om kassakön inte är tom får den kund C’ som stått längst i kö (den som i normala fall
		 * står först) gå till den lediga kassan som därefter omedelbart åter blir upptagen. Vi
		 * genererar sen en betalningshändelse för C’. Om kassakön istället är tom ökar vi på
		 * antalet lediga kassor med 1.
		 */
		
	}
}
