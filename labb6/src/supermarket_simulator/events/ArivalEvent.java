package supermarket_simulator.events;
import generic_simulator.Event;
import generic_simulator.EventQueue;
import generic_simulator.State;

class ArivalEvent extends Event {
	public ArivalEvent(double time) {
		super(time);
	}
	
	@Override
	public void execute(State state, EventQueue eventQueue) {
		super.execute(state, eventQueue);
		
		/* TODO
		 * En kund C som anländer släpps in endast om snabbköpet ännu är öppet och det finns
         * plats. Släpps C in är det en till kund inne i snabbköpet som kommer att handa varor.
         * 
		 * Om det är öppet men fullt så avviker C istället utan att handla och har vi missat en kund.
         * En kund som anländer efter stängning ska däremot inte räknas som en missad kund.
         * 
         * Först ska C plocka ihop sina varor. Vi skapar en framtida plockhändelse för C (se nedan)
         * som inträffar när C plockat klart och gått bort till kassorna. Denna läggs till händelsekön.
         * Under förutsättning att snabbköpet har öppet ska slutligen också en (1) ny framtida
         * ankomsthändelse (för en ny kund) skapas och lagras i händelsekön.
         * 
         * Att varje ankomsthändelse genererar nästa gör att vi dels vet att det alltid finns en
         * ankomsthändelse i kön dels minskar behovet av minne som kön behöver. Sämre vore att
         * generera alla framtida ankomsthändelser innan simuleringen startar, något som alltså
         * inte ska göras.
		 */
		
	}
}