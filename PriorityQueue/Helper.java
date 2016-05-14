
import java.util.Iterator;

/**
 * class is a basic helper class with most used functionalities
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 11:35 pm
 * 
 */
public class Helper {

	/**
	 * Returns the next element if available or null if not using the iterator.
	 * 
	 * @param iterator
	 *            : Iterator<Vertex> : input iterator which has to be iterated
	 * @return : Vertex : returns the element if present otherwise null
	 */
	static Vertex next(Iterator<Vertex> iterator) {
		if (iterator.hasNext())
			return iterator.next();
		else
			return null;
	}
	
	/**
	 * Resets the graph to its initial state, effectively updates parent
	 * and distance fields
	 * 
	 * @param g
	 *            : Graph : graph which has to be reset
	 */
	static void resetGraph(Graph g) {
		Iterator<Vertex> vIterator = g.iterator();
		Vertex current = next(vIterator);
		while (current != null) {
			current.distance = Integer.MAX_VALUE;
			current.seen = false;
			current.parent = null;
			current = next(vIterator);
		}
	}
	
	/**
	 * Prints the input graph
	 * @param g : Graph : input graph
	 */
	static void printGraph(Graph g){
	       for(Vertex v : g.verts){
	    	   if(v != null)
	    	   for(Edge e : v.Adj)
	    		   System.out.println("("+e.To+", "+e.From+")");
	       }
	}
}
