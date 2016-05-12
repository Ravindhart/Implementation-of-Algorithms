/**
 * Class to represent a vertex of a graph
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/12/2016 10:32 am
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Index<Edge>, Comparable<Vertex> {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public int distance; // distance to the vertex from the source vertex
    public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
    public int degree; //maintains the count of out degree, used in processing euler tours
    public Entry<Edge> index;
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
	revAdj = new ArrayList<Edge>();   /* only for directed graphs */
	index = null;
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
	return Integer.toString(name);
    }

	@Override
	public void putIndex(Entry<Edge> t) {
		index = t;
	}

	@Override
	public Entry<Edge> getIndex() {
		return index;
	}

	@Override
	public int compareTo(Vertex v) {
		return this.name - v.name;
	}
}
