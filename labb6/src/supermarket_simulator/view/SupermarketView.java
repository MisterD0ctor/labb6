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
			double maxPickTime, double minPayTime, double maxPayTime, long seed) {

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
	public void update(Observable o, Object arg) {
		SupermarketState state = (SupermarketState) o;
		Event event = (Event) arg;

		printEvent(event, state);

		if (event instanceof StopEvent) {
			printResult(state);
		}

	}

	private void printEvent(Event event, SupermarketState state) {
		String eventName = eventName(event);

		if (event instanceof StartEvent || event instanceof StopEvent) {
			System.out.printf("%6.2f %s \r\n", event.time(), eventName);
		} else {
			String open = state.isClosed() ? "S" : "Ö";
			System.out.printf("%6.2f %-9s %4s  %s %4d %7.2f %4d %5d %5d %6d %7.2f %6d  %s\r\n", event.time(), eventName,
					customerName(event), open, state.idleCheckouts(), state.idleCheckoutTime(), state.customers(),
					state.visits(), state.missedCustomers(), state.queuedCustomers(), state.queueingTime(),
					state.queueingCustomers(), state.queueToString());
		}
	}

	private void printResult(SupermarketState store) {
		double avrageIdleCheckoutTime = store.idleCheckoutTime() / store.checkouts();
		double avrageQueueingTime = store.queueingTime() / store.queuedCustomers();
		System.out.printf(
				"\r\nRESULTAT\r\n" + "========\r\n" + "1) Av %d kunder handlade %d medan %d missades.\r\n"
						+ "2) Total tid %d kassor varit lediga: %.2f te.\r\n"
						+ "Genomsnittlig ledig kassatid: %.2f te (dvs %.2f%% av tiden från öppning tills sista kunden "
						+ "betalat).\r\n" + "3) Total tid %d kunder tvingats köa: %.2f te.\r\n"
						+ "Genomsnittlig kötid: %.2f te.",
				store.attemptedVisits(), store.visits(), store.missedCustomers(), store.checkouts(),
				store.idleCheckoutTime(), avrageIdleCheckoutTime, avrageIdleCheckoutTime / store.time() * 100.0,
				store.queuedCustomers(), store.queueingTime(), avrageQueueingTime);
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
