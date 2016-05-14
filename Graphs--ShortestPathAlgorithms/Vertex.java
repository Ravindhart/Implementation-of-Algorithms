/**
 * Class to represent a vertex of a graph
 * 
 *
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Vertex implements Index, Comparator<Vertex> {
    public int name; // name of the vertex
    public boolean seen; // flag to check if the vertex has already been visited
    public Vertex parent; // parent of the vertex
    public Distance distance; // distance to the vertex from the source vertex
    public int degree; //represents the in-degree of this vertex
    public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
	public int index; // refers to index of this vertex when used with IndexedHeap
	public int count; //represents no of times this vertex has been processed in bellman ford
	public int spCount;  //represents the count of shortest paths to this vertex from source.
	public boolean isSPCalculated; //represents if the shortest paths are calculated for this vertex
	public boolean forCycle; //though its like a redundant, this is created to avoid resetting of seen variable for a small case.
	public VisitStatus visit;
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
	distance = new Distance();
	degree = 0;
	count = 0;
	spCount = 0;
	isSPCalculated = false;
	forCycle = false;
	visit = VisitStatus.WHITE;
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
	public int compare(Vertex v1, Vertex v2) {
		if(v1.distance.isInfinity && v2.distance.isInfinity)
			return 0;
		else if(v1.distance.isInfinity)
			return 1;
		else if(v2.distance.isInfinity)
			return -1;
		else{
			int compare = v1.distance.value - v2.distance.value;
			if(compare > 0)
				return 1;
			else if(compare < 0)
				return -1;
			else
				return 0;
		}
			
	}

}
