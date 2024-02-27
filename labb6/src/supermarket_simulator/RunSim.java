package supermarket_simulator;

public class RunSim {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		StoreState state = new StoreState(
				Integer.parseInt(args[0]),
				Integer.parseInt(args[1]),
				Double.parseDouble(args[2]),
				Double.parseDouble(args[3]),
				Double.parseDouble(args[4]),
				Double.parseDouble(args[5]),
				Double.parseDouble(args[6]),
				Integer.parseInt(args[7]));
		
		StoreView view = new StoreView();
		
		state.addObserver(view);
		
		
		
	}

}
