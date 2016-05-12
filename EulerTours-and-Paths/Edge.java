/**
 * Class that represents an arc in a Graph
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/12/2016 10:35 am
 */
public class Edge implements Comparable<Edge>{
    public Vertex From; // head vertex
    public Vertex To; // tail vertex
    public int Weight;// weight of the arc
    public boolean visited; //true if this edge is already processed, false otherwise

    /**
     * Constructor for Edge
     * 
     * @param u
     *            : Vertex - The head of the arc
     * @param v
     *            : Vertex - The tail of the arc
     * @param w
     *            : int - The weight associated with the arc
     */
    Edge(Vertex u, Vertex v, int w) {
	From = u;
	To = v;
	Weight = w;
	visited = false;
    }

    /**
     * Method to find the other end end of the arc given a vertex reference
     * 
     * @param u
     *            : Vertex
     * @return
     */
    public Vertex otherEnd(Vertex u) {
	// if the vertex u is the head of the arc, then return the tail else return the head
	if (From == u) {
	    return To;
	} else {
	    return From;
	}
    }

    /**
     * Method to represent the edge in the form (x,y) where x is the head of
     * the arc and y is the tail of the arc
     */
    public String toString() {
	return "(" + From + "," + To + ")";
    }

	@Override
	public int compareTo(Edge o) {
        if(this.To.compareTo(o.To) == 0)
        	return 0;
        else if(this.To.compareTo(o.From) == 0)
        	return 0;
        else if(this.From.compareTo(o.To) == 0)
        	return 0;
        else if(this.From.compareTo(o.From) == 0)
        	return 0;
		return 1;
	}

}
