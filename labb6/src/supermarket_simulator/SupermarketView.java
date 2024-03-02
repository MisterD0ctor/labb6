package supermarket_simulator;

import java.util.Observable;

import generic_simulator.*;
import supermarket_simulator.SupermarketState;
import supermarket_simulator.events.*;

@SuppressWarnings("deprecation")
class SupermarketView extends View {

	public SupermarketView(int openCheckouts, int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, long seed) {

		System.out.printf("PARAMETRAR\r\n" s
						+ "==========\r\n" 
						+ "Antal kassor, N..........: %d\r\n"
						+ "Max som ryms, M..........: %d\r\n" 
						+ "Ankomshastighet, lambda..: %f\r\n"
						+ "Plocktider, [P_min..Pmax]: [%f..%f]\r\n" 
						+ "Betaltider, [K_min..Kmax]: [%f..%f]\r\n"
						+ "Frö, f...................: %d",
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
		
		System.out.printf(
				"%f %s      %s  %s    %d    %f    %d     %d     %d      %d    %f      %d  %s",
				event.time(), eventName, event.customer);
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
}
