package generic_simulator;

import java.util.PriorityQueue;

import generic_simulator.model.Event;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class EventQueue {

	private PriorityQueue<Event> queue; // En prioritetskö för händelser

	public EventQueue() {
		queue = new PriorityQueue<Event>((e1, e2) -> Double.compare(e1.time(), e2.time()));
	}

	public void enqueue(Event event) {
		queue.offer(event); // Lägg till händelsen i kön
	}

	protected Event dequeue() {
		return queue.poll(); // Ta bort och returnera den första händelsen i kön //Ifall tom så returnerar
								// den null
	}

	public boolean isEmpty() {
		return queue.isEmpty(); // Kontrollera om kön är tom
	}
}
