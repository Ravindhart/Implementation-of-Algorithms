

/**
 * Class to represent a vertex of a graph
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 11:20 pm
 */

import java.util.*;

public class Vertex {
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public Vertex parent; // parent of the vertex
	public int distance; // distance to the vertex from the source vertex
	public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or
									// ArrayList
	public Vertex mate; // represents the mate if this node is part of matched
						// edge
	public int type; // 1 represents inner, 2 represents outer
	public boolean isActive;
	public Vertex blossomV;
	public Vertex root;
	public boolean isBlossom;


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
		mate = null;
		type = 0;
		isActive = true;
		blossomV = null;
	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Vertex v = (Vertex) obj;
		if (this.name == v.name)
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return this.name;
	}

	
}
