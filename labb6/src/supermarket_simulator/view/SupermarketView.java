package supermarket_simulator.view;

import java.util.Observable;

import generic_simulator.model.Event;
import generic_simulator.model.StopEvent;
import generic_simulator.view.View;
import supermarket_simulator.model.*;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
@SuppressWarnings("deprecation")
public class SupermarketView extends View {

	public SupermarketView(int openCheckouts, int customerCapacity, double arivalFrequency, double minPickTime,
			double maxPickTime, double minPayTime, double maxPayTime, long seed) { // Samma parametrar som i SupermarketState

		System.out.printf(
				"PARAMETRAR\r\n" 
						+ "==========\r\n" 
						+ "Antal kassor, N..........: %d \r\n"
						+ "Max som ryms, M..........: %d \r\n" 
						+ "Ankomshastighet, lambda..: %.2f \r\n"
						+ "Plocktider, [P_min..Pmax]: [%.2f..%.2f] \r\n" 
						+ "Betaltider, [K_min..Kmax]: [%.2f..%.2f] \r\n"
						+ "Frö, f...................: %d \r\n\r\n",
				openCheckouts, customerCapacity, arivalFrequency, minPickTime, maxPickTime, minPayTime, maxPayTime,
				seed);

		System.out.print("FÖRLOPP\r\n" + "=======\r\n"
				+ "   Tid Händelse  Kund  ?  led    ledT    I     $    :-(   köat    köT   köar  [Kassakö..]\r\n");
	}

	@Override
	public void update(Observable o, Object arg) { // Skickar in Eventet som argument, o är supermarketstate.
		SupermarketState state = (SupermarketState) o; // Kolla att o är supermarketstate
		Event event = (Event) arg; // Kolla att arg är ett event

		printEvent(event, state); 

		if (event instanceof StopEvent) { 
			printResult(state);
		}

	}

	private void printEvent(Event event, SupermarketState state) {
		String eventName = getEventName(event);

		if (event instanceof StartEvent || event instanceof StopEvent) {
			System.out.printf("%6.2f %s \r\n", event.time(), eventName);
		} else {
			String open = state.isOpen() ? "Ö" : "S"; // Ifall öppet = ö, annars stängt = s
			System.out.printf("%6.2f %-9s %4s  %s %4d %7.2f %4d %5d %5d %6d %7.2f %6d  %s\r\n", event.time(), 
					eventName, getCustomerName(event), open, state.idleCheckouts(), state.idleCheckoutTime(), 
					state.customerCount(), state.visits(), state.missedCustomers(), state.queuedCustomers(), 
					state.queueingTime(), state.queueingCustomers(), state.queueToString());
		}
	}

	private void printResult(SupermarketState state) {
		double avrageIdleCheckoutTime = state.idleCheckoutTime() / state.checkoutCount();
		double idleCheckoutTimePercent = avrageIdleCheckoutTime / state.lastCheckoutTime() * 100.0;
		double avrageQueueingTime = state.queueingTime() / state.queuedCustomers();
		System.out.printf(
				"\r\nRESULTAT\r\n" 
						+ "========\r\n" 
						+ "1) Av %d kunder handlade %d medan %d missades.\r\n\r\n"
						+ "2) Total tid %d kassor varit lediga: %.2f te.\r\n\r\n"
						+ "Genomsnittlig ledig kassatid: %.2f te (dvs %.2f%% av tiden från öppning tills sista kunden "
						+ "betalat). \r\n\r\n" 
						+ "3) Total tid %d kunder tvingats köa: %.2f te.\r\n"
						+ "Genomsnittlig kötid: %.2f te.",
					state.attemptedVisits(), state.visits(), state.missedCustomers(), state.checkoutCount(),
					state.idleCheckoutTime(), avrageIdleCheckoutTime, idleCheckoutTimePercent,
					state.queuedCustomers(), state.queueingTime(), avrageQueueingTime);
	}

	private String getEventName(Event event) {
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

	private String getCustomerName(Event event) {
		if (event instanceof ArivalEvent) {
			return ((ArivalEvent) event).customer.toString();
		} else if (event instanceof PickEvent) {
			return ((PickEvent) event).customer.toString();
		} else if (event instanceof PayEvent) {
			return ((PayEvent) event).customer.toString();
		} else if (event instanceof CloseEvent) {
			return "---";
		} else {
			return "";
		}
	}
}
