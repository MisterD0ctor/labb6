package supermarket_simulator;
import java.util.Observable;

import generic_simulator.*;
import supermarket_simulator.*;
import supermarket_simulator.events.*;

@SuppressWarnings("deprecation")
class SupermarketView extends View {
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof StartEvent) {
			System.out.println("Start, ");
		} else if (arg instanceof ArivalEvent) {
			System.out.println("Ankomst, " + ((ArivalEvent) arg).customer.id);
		} else if (arg instanceof PickEvent) { 
			System.out.println("Plock, " + ((PickEvent) arg).customer.id);
		} else if (arg instanceof PayEvent) { 
			System.out.println("Betalning, " + ((PayEvent) arg).customer.id);
		} else if (arg instanceof CloseEvent) { 
			System.out.println("Stänger");
		} else if (arg instanceof StopEvent) { 
			System.out.println("Stop");
		} else {			
			System.out.println(arg);
		}
	}
}
