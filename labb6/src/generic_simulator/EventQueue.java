package generic_simulator;

import java.util.PriorityQueue;

import generic_simulator.model.Event;

/**
 * A queue that stores events and sorts them based on their time (lowest time first)
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 */
public class EventQueue {

	private PriorityQueue<Event> queue; // En prioritetskö för händelser

	/**
	 * Creates an empty queue
	 */
	public EventQueue() {
		queue = new PriorityQueue<Event>((e1, e2) -> Double.compare(e1.time(), e2.time()));
	}

	/**
	 * Adds an event to the queue
	 * @param event The event to be added
	 */
	public void enqueue(Event event) {
		queue.offer(event); // Lägg till händelsen i kön
	}

	/**
	 * 
	 * @return The event in the queue with the lowest time
	 */
	protected Event dequeue() {
		return queue.poll(); // Ta bort och returnera den första händelsen i kön 
		//Ifall tom så returnerar den null
	}

	/**
	 * 
	 * @return true if and only if the queue contains no events
	 */
	public boolean isEmpty() {
		return queue.isEmpty(); // Kontrollera om kön är tom
	}
}
