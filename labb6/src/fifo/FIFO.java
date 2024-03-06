package fifo;

import java.util.ArrayList;

/**
 * An imlementation of a first-in first-out queue.
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 * @param <E> The type stored in this queue
 */
public class FIFO<E> {

	private ArrayList<E> list;

	/**
	 * Crates a new empty queue
	 */
	public FIFO() {
		list = new ArrayList<E>();
	}

	/**
	 * Places an element last in the queue
	 * @param e The element to be added
	 */
	public void enqueue(E e) {
		list.add(e);
	}

	/**
	 * Gets and removes the first element in the queue
	 * @return The first element in the queue
	 */
	public E dequeue() {
		if (list.isEmpty()) {
			return null;
		} else {
			return list.remove(0);
		}
	}

	/**
	 * 
	 * @return true if and only if the queue contains no elements
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * 
	 * @return The number of elements in the queue
	 */
	public int size() {
		return list.size();
	}

	@Override
	public boolean equals(Object o) {
		return this.list.equals(o);
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
