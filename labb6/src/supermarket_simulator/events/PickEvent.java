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
        
        StoreState store = (StoreState) state;
        
        // Kontrollera om det finns lediga kassor
        if(store.availableCheckoutsCount > 0) {
            //Minskar antalet lediga kassor eftersom, en till är upptagen nu
            store.availableCheckoutsCount -= 1;
            
            Event payEvent = new PayEvent(store.payTimeProvider.next(), customer);
            eventQueue.enqueue(payEvent);
        } else { 

            // Alla kassor är upptagna, ställ kunden i kö
            store.checkoutQueue.offer(customer);
            store.totalQueueTime -= time;
        }
    }
}
//Ludvig tar denna