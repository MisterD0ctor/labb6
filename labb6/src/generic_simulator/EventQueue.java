package generic_simulator;

import java.util.PriorityQueue; //TILLAGD


public class EventQueue {

	private PriorityQueue<Event> queue; // En prioritetskö för händelser
	
	public EventQueue() {
        queue = new PriorityQueue<>((e1, e2) -> Double.compare(e1.time, e2.time));
    }
		
	public void enqueue(Event event) {
		queue.offer(event); // Lägg till händelsen i köen	
	}
	
	Event dequeue() {
		return queue.poll(); // Ta bort och returnera den första händelsen i köen //Ifall tom så returnerar den null
	}
	
	//NYTT HELA VÄGEN NER FÖR ATT SE IFALL DET ÄR TOMT
	public boolean isEmpty() {
        return queue.isEmpty(); // Kontrollera om köen är tom
    }
}
//DAVID TAR DENNA