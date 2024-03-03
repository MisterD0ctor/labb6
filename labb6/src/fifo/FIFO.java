package fifo;

import java.util.ArrayList;

/**
 * @author David Sämfors, Ludvig Pernsköld, Kasper Axelsson & Zeb Muhlbach
 * @param <E> The type stored in this queue
 */
public class FIFO<E> {

	private ArrayList<E> list;

	public FIFO() {
		list = new ArrayList<E>();
	}

	public void enqueue(E e) {
		list.add(e);
	}

	public E dequeue() {
		if (list.isEmpty()) {
			return null;
		} else {
			return list.remove(0);
		}
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

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
