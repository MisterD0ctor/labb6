package supermarket_simulator;
import java.util.Observable;

import generic_simulator.View;

@SuppressWarnings("deprecation")
class StoreView extends View {
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}
}
