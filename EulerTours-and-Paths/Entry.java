	/**
	 * class which represents an entry in linked list.
	 *
	 * @param <T>
	 *            : Object which extends Comparable in its hierarchy
	 */
	public class Entry<T> {
		T element;
		Entry<T> next, prev;

		Entry(T x, Entry<T> prv, Entry<T> nxt) {
			element = x;
			next = nxt;
			prev = prv;
		}
	}