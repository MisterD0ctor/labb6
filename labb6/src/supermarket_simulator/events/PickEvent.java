package supermarket_simulator.events;

import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;
import supermarket_simulator.Customer;
import supermarket_simulator.StoreState;

public class PickEvent extends Event {
    
    private Customer customer;

    public PickEvent(double time, Customer customer) {
        super(time);
        this.customer = customer;
    }
    
    @Override
    public void execute(State state, EventQueue eventQueue) {
        super.execute(state, eventQueue);
        
        StoreState storeState = (StoreState) state;
        
        
        
        // Kontrollera om det finns lediga kassor
        if(storeState.availableCheckoutsCount > 0) {
            //Minskar antalet lediga kassor eftersom, en till är upptagen nu
            storeState.availableCheckoutsCount -= 1;
            
           /* TEST EXEMPEL
            double payTime = this.getTime() + storeState.payTime.getNext();
            Event payEvent = new PayEvent(payTime, customer);
            eventQueue.add(payEvent);
        } else {*/
            
            // Alla kassor är upptagna, ställ kunden i kö
            storeState.checkoutQueue.add(customer);
        }
    }
	
}
//Ludvig tar denna