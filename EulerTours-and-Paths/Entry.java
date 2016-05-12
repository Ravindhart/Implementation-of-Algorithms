	/**
	 * class which represents an entry in linked list.
	 * @author Ravindhar Reddy Thallapureddy
         * Last modified on : 5/12/2016 10:36 am
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
