/**
 * class is a basic helper class with most used functionalities
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/12/2016 10:34 am
 */
import java.util.Iterator;


public class Helper {

	/**
	 * Returns the next element if available or null if not using the iterator.
	 * 
	 * @param iterator
	 *            : Iterator<Vertex> : input iterator which has to be iterated
	 * @return : Vertex : returns the element if present otherwise null
	 */
	static <T> T next(Iterator<T> iterator) {
		if (iterator.hasNext())
			return iterator.next();
		else
			return null;
	}

	/**
	 * Prints the input graph
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	static void printGraph(Graph g) {
		for (Vertex v : g.verts) {
			if (v != null)
				for (Edge e : v.Adj)
					System.out.println("(" + e.To + ", " + e.From + ")");
		}
	}

	/**
	 * Returns an unprocessed edge from the adjacency list of vertex v.
	 * 
	 * @param v
	 *            : Vertex : input Vertex
	 * @return : Edge : unprocessed edge
	 */
	static Edge getEdge(Vertex v){
		int index = v.degree;
		if(index == 0)
			return null;
		index--;
		Edge e =  v.Adj.get(index);
		while(e.visited && index > 0){
			index--;
			e = v.Adj.get(index);
		}
		v.degree = index;
	   if(!e.visited)
		   return e;
	   return null;
	}

}
