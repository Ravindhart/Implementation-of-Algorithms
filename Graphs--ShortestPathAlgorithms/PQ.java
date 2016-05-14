
/**
 * Interface for Priority Queue. The user of this interface will have precise
 * control over operations like insert, add, min, peek, deleteMin, remove
 * elements from queue
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 10:53 pm
 */
 * @param <T> : Object of any type
 */
public interface PQ<T> {
	
	/**
	 * Inserts the specified element into this PriorityQueue.
	 * @param x : T : element which has to be inserted into PQ
	 */
	public void insert(T x);

	/**
	 * Retrieves and removes the head of this queue.
	 * @return : T : element returned, null if queue is empty
	 */
	public T deleteMin();

	/**
	 * Retrieves the head of the queue without removing the element from queue.
	 * @return : T : element returned, null if queue is empty
	 */
	public T min();

	/**
	 * Inserts the specified element into this PriorityQueue.
	 * @param x : T : element which has to be inserted into PQ
	 */
	public void add(T x);

	/**
	 * Retrieves and removes the head of this queue.
	 * @return : T : element returned, null if queue is empty
	 */
	public T remove();

	/**
	 * Retrieves the head of the queue without removing the element from queue.
	 * @return : T : element returned, null if queue is empty
	 */
	public T peek();
}
