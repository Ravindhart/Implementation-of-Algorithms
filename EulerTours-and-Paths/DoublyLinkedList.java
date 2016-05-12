/**
* class for DoublyLinkedList
* 
* @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
*
*/
public class DoublyLinkedList<T> {
	Entry<T> header, tail;
	int size;

	public DoublyLinkedList() {
		header = new Entry<T>(null, null, null);
		tail = null;
		size = 0;
	}
	
	/**
	 * Adds an entry to linked list.
	 * @param x : T : an element which has to be added to linked list.
	 */
	Entry<T> add(T x) {
		if (tail == null) {
			Entry<T> temp = header;
			header.next = new Entry<T>(x, temp, null);
			tail = header.next;
		} else {
			Entry<T> temp = tail;
			tail.next = new Entry<T>(x, temp, null);
			tail = tail.next;
		}
		size++;
		return tail;
	}
	
	/**
	 * Prints the linked list.
	 */
	void printList() {
		Entry<T> x = header.next;
		while (x != null) {
			System.out.println(x.element + " ");
			x = x.next;
		}
	}
}
