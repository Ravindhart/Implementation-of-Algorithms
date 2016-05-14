import java.util.Comparator;

/**
 * class for IndexedHeap i.e. simple class which extends BinaryHeap in addition
 * to maintaining the index of each element.
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
 *
 * @param <T>
 *            : any object that extends Index
 */
public class IndexedHeap<T extends Index> extends BinaryHeap<T> {

	/**
	 * Constructs an indexed heap with the input array of objects and uses
	 * comparator for comparing objects of array.
	 * 
	 * @param q
	 *            : T[] : array of objects
	 * @param comp
	 *            : Comparator<T> : comparator
	 */
	IndexedHeap(T[] q, Comparator<T> comp) {
		super(q, comp);
	}

	/**
	 * Constructs an empty indexed heap with initial capacity n and uses the
	 * comparator comp
	 * 
	 * @param n
	 *            : int : initial capacity of heap
	 * @param comp
	 *            : Comparator<T> : comparator for comparing objects of heap
	 */
	IndexedHeap(int n, Comparator<T> comp) {
		super(n, comp);
	}

	/**
	 * Restores the heap invariant after the priority of an element is decresed
	 * 
	 * @param x
	 *            : T : element whose priority has been decreased and which
	 *            might cause heap order violation
	 */
	void decreaseKey(T x) {
		percolateUp(x.getIndex());
	}

	/**
	 * Updates the array by inserting input element at specified index and
	 * appropriately updates the index property of element.
	 * 
	 * @param element
	 *            : T : input element which has to be inserted
	 * @param index
	 *            : int : index of the array at which the element has to be
	 *            inserted
	 */
	void assignElement(T element, int index) {
		super.assignElement(element, index);
		if (element == null)
			System.out.println("Wait here");
		element.putIndex(index);
	}
}
