package supermarket_simulator.view;

import java.util.Observable;

import generic_simulator.model.Event;
import generic_simulator.model.StopEvent;
import generic_simulator.view.View;
import supermarket_simulator.model.*;

@SuppressWarnings("deprecation")
public class SupermarketView extends View {

	public SupermarketView(int openCheckouts, int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, long seed) {

		System.out.printf("PARAMETRAR\r\n"
						+ "==========\r\n" 
						+ "Antal kassor, N..........: %d\r\n"
						+ "Max som ryms, M..........: %d\r\n" 
						+ "Ankomshastighet, lambda..: %.2f\r\n"
						+ "Plocktider, [P_min..Pmax]: [%.2f..%.2f]\r\n" 
						+ "Betaltider, [K_min..Kmax]: [%.2f..%.2f]\r\n"
						+ "Frö, f...................: %d\r\n\r\n",
				openCheckouts, customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime, maxPayTime,
				seed);
		
		System.out.print("FÖRLOPP\r\n"
					+ "=======\r\n"
					+ "   Tid Händelse  Kund  ?  led    ledT    I     $    :-(   köat    köT   köar  [Kassakö..]\r\n");
	}

	@Override
	public void update(Observable o, Object arg) {
		SupermarketState store = (SupermarketState)o;
		Event event = (Event)arg;
		String eventName = eventName(event);
		String customerName = customerName(event);
		String open = store.isClosed() ? "S" : "Ö";
		
		//if (event instanceof SupermarketEvent) {
			System.out.printf("%6.2f %-9s %4s  %s %4d %7.2f %4d %5d %5d %6d %7.2f %6d  %s\r\n", 
					event.time(), eventName, customerName, open, store.idleCheckouts(), store.idleCheckoutsTime(), 
					store.customers(), store.payingCustomers(), store.missedCustomers(), store.queuedCustomers(), 
					store.queueingTime(), store.queueingCustomers(), store.queueToString());
		//} else {
		//	System.out.printf("%6.2f %s \r\n", event.time(), eventName);
		//}
		
		
	}
	
	private String eventName(Event event) {
		if (event instanceof StartEvent) {
			return "Start";
		} else if (event instanceof ArivalEvent) {
			return "Ankomst";
		} else if (event instanceof PickEvent) { 
			return "Plock";
		} else if (event instanceof PayEvent) { 
			return "Betalning";
		} else if (event instanceof CloseEvent) { 
			return "Stänger";
		} else if (event instanceof StopEvent) { 
			return "Stop";
		} else {			
			return "Event";
		}
	}
	
	private String customerName(Event event) {
		if (event instanceof StartEvent) {
			return "";
		} else if (event instanceof ArivalEvent) {
			return ((ArivalEvent)event).customer.toString();
		} else if (event instanceof PickEvent) { 
			return ((PickEvent)event).customer.toString();
		} else if (event instanceof PayEvent) { 
			return ((PayEvent)event).customer.toString();
		} else if (event instanceof CloseEvent) { 
			return "---";
		} else if (event instanceof StopEvent) { 
			return "";
		} else {
			return "";
		}
	}
}
