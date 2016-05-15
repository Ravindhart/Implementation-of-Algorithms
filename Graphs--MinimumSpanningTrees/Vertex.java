/**
 * Class to represent a vertex of a graph
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/15/2016 12:09 pm
 */

import java.util.*;

public class Vertex implements Index, Comparator<Vertex> {
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public Vertex parent; // parent of the vertex
	public int distance; // distance to the vertex from the source vertex
	public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or
									// ArrayList

	public int index; // refers to index of this vertex when used with
						// IndexedHeap

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
		name = n;
		seen = false;
		parent = null;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>(); /* only for directed graphs */
		distance = Integer.MAX_VALUE; // for MST2
	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}

	@Override
	/**
	 * Updates the index of this vertex with the input index
	 */
	public void putIndex(int index) {
		this.index = index;

	}

	@Override
	/**
	 * Returns the index of this vertex
	 */
	public int getIndex() {
		return index;
	}

	@Override
	/**
	 * Compares its two arguments for order.  Returns a negative integer,
	 * zero, or a positive integer as the first argument is less than, equal
	 * to, or greater than the second. ~ java doc
	 */
	public int compare(Vertex o1, Vertex o2) {
		return o1.distance - o2.distance;
	}
}
