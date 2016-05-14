
import java.util.Arrays;
import java.util.Comparator;

/**
 * class for implementing Priority Queue
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
 *
 * @param <T>
 *            : Object of any type
 */
public class BinaryHeap<T> implements PQ<T> {
	T[] pq;
	Comparator<T> c;
	int size;

	/**
	 * Constructs a binaryHeap with the input array of objects and uses
	 * comparator for comparing objects of array.
	 * 
	 * @param q
	 *            : T[] : array of objects
	 * @param comp
	 *            : Comparator<T> : comparator which has to be used in
	 *            maintaining the heap
	 */
	BinaryHeap(T[] q, Comparator<T> comp) {
		pq = q;
		c = comp;
		size = pq.length - 1;
		buildHeap();
	}

	/**
	 * Constructs an empty binary heap with initial capacity n and uses the
	 * comparator comp
	 * 
	 * @param n
	 *            : int : initial capacity of binary heap
	 * @param comp
	 *            : Comparator<T> : comparator for comparing objects of heap
	 */
	BinaryHeap(int n, Comparator<T> comp) {
		pq = (T[]) new Object[n];
		c = comp;
		size = 0;
	}

	/**
	 * Inserts the specified element into this PriorityQueue.
	 * 
	 * @param x
	 *            : T : element which has to be inserted into PQ
	 */
	public void insert(T x) {
		add(x);
	}

	/**
	 * Retrieves and removes the head of this queue.
	 * 
	 * @return : T : element returned, null if queue is empty
	 */
	public T deleteMin() {
		return remove();
	}

	/**
	 * Retrieves the head of the queue without removing the element from queue.
	 * 
	 * @return : T : element returned, null if queue is empty
	 */
	public T min() {
		return peek();
	}

	/**
	 * Inserts the specified element into this PriorityQueue.
	 * 
	 * @param x
	 *            : T : element which has to be inserted into PQ
	 */
	public void add(T x) {
		int n = pq.length;
		n--;
		if (size == n)
			resize();
		size++;

		pq[size] = x;
		// Invariant : puts an element at the end of the array and promotes the
		// element up the tree till it finds a right place or it will be the
		// root.
		percolateUp(size);
	}

	/**
	 * Retrieves and removes the head of this queue.
	 * 
	 * @return : T : element returned, null if queue is empty
	 */
	public T remove() {
		if (size <= 0)
			return null;

		T min = pq[1];

		pq[1] = pq[size--];
		//assignElement(pq[size--], 1);
		// Invariant : removes the head of the tree and puts the last element of
		// the tree at the root and demotes it using percolateDown till it finds
		// a right place or it will be a leaf
		percolateDown(1);
		return min;
	}

	/**
	 * Retrieves the head of the queue without removing the element from queue.
	 * 
	 * @return : T : element returned, null if queue is empty
	 */
	public T peek() {
		if (size <= 0)
			return null;

		return pq[1];
	}

	/**
	 * Maintains the heap invariant by promoting the element at index i up the
	 * tree until it is greater than or equal to its parent, or is the root.
	 * 
	 * @param i
	 *            : int : index of the element to be promoted
	 */
	void percolateUp(int i) {
		assignElement(pq[i], 0);

		// Invariant: Initially we put the element of index i at index 0, and in
		// each iteration, we compare the element with its parent for the heap
		// invariant till we find an appropriate location for the element.
		while (c.compare(pq[i / 2], pq[0]) > 0) {
			assignElement(pq[i / 2], i);
			i = i / 2;
		}
		assignElement(pq[0], i);
	}

	/**
	 * Maintains maintaining heap invariant by demoting element at index i down
	 * the tree repeatedly until it is less than or equal to its children or is
	 * a leaf.
	 * 
	 * @param i
	 *            : int : index of the element which might be violating heap
	 *            invariant and has to be demoted
	 */
	void percolateDown(int i) {
		T temp = pq[i];
		int childIndex = 2 * i;

		// Invariant : In each iteration, we compare the element with its
		// children till we find a location where this element fits maintaining
		// the heap invariant
		while (childIndex <= size) {

			// Single child case (can only be a left child)
			if (childIndex == size) {
				if (c.compare(temp, pq[childIndex]) > 0) {
					assignElement(pq[childIndex], i);
					i = childIndex;
				} else
					break;
			} else { // two childs, takes the least of both and uses it for
						// comparison
				int sChildVal;
				if (c.compare(pq[childIndex], pq[childIndex + 1]) >= 0)
					sChildVal = childIndex+1;
				else
					sChildVal = childIndex;
				if (c.compare(temp, pq[sChildVal]) > 0) {
					assignElement(pq[sChildVal], i);
					i = sChildVal;
				} else
					break;
			}
			childIndex = 2 * i;
		}
		assignElement(temp, i);
		return;
	}

	/**
	 * Builds the heap using the array pq and comparator c. Verifies the heap
	 * property in bottom up fashion, so effectively starting from n/2 th
	 * element, as last level (imagine binary heap tree) will all be leafs
	 */
	void buildHeap() {
		int i;
		// Invariant: Starts from the n/2th element, as last level (n/2 +1 .. n)
		// are all leafs. For each element, percolateDown is executed which puts
		// the element being considered in appropriate location.
		for (i = size / 2; i >= 1; i--)
			percolateDown(i);
	}

	/**
	 * Updates the array by inserting input element at specified index.
	 * 
	 * @param element
	 *            : T : input element which has to be inserted
	 * @param index
	 *            : int : index of the array at which the element has to be
	 *            inserted
	 */
	void assignElement(T element, int index) {
		pq[index] = element;
	}

	/**
	 * Increases the capacity of the heap by doubling the size of the array.
	 */
	void resize() {
		int n = pq.length;
		pq = Arrays.copyOf(pq, 2 * n);
	}

	/**
	 * Checks if the heap is empty
	 * 
	 * @return : boolean : true if the heap is empty, false otherwise
	 */
	boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Sorts the array A using the comparator comp. Uses the BinaryHeap to
	 * perform the sorting and so the resultant array will be ascending if the
	 * comparator is for MaxHeap and descending if the heap is minHeap.
	 * 
	 * @param A
	 *            : T[] : array of elements to be sorted
	 * @param comp
	 *            : Comparator<T> : comparator used for sorting
	 */
	public static <T> void heapSort(T[] A, Comparator<T> comp) {
		BinaryHeap<T> binaryHeap = new BinaryHeap<T>(A, comp);
		int n = A.length;
		n--;
		int i;
		// Invariant: A[i] holds the head of the heap. In each
		// iteration, we remove the head of the heap and place the element in
		// the array from end. So, if the heap is min heap, the array will be
		// sorted in descending order and if the heap is max heap, array will be
		// ascending sorted.
		for (i = n; i > 0; i--) {
			A[i] = binaryHeap.remove();
		}
	}
}
