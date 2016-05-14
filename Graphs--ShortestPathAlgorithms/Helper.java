import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * class is a basic helper class with most used functionalities
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 10:55 pm
 */
 */
public class Helper {
	public static final String HYPEN = "-";
	public static final String SPACE = " ";
	public static final String INFINITY = "INF";
	public static final String BFALGO = "B-F";
	public static final String LESSER = "<";
	public static final String CYC_MESSAGE = "Non-positive cycle in graph. DAC is not applicable";

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
	 * Resets the graph to its initial state, effectively updates degree and
	 * seen flag.
	 * 
	 * @param g
	 *            : Graph : graph which has to be reset
	 */

	static void resetGraph(Graph g) {
		Iterator<Vertex> vIterator = g.iterator();
		Vertex current = next(vIterator);
		while (current != null) {
			current.seen = false;
			current.degree = current.revAdj.size();
			current = next(vIterator);
		}
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
	 * Initializes the graph, by reseting parent, seen, count, distance fields
	 * of every vertex in graph, later initializes the source by setting the
	 * distance to 0
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @param src
	 *            : Vertex : source vertex
	 */
	static void initialize(Graph g, Vertex src) {
		Iterator<Vertex> vIterator = g.iterator();
		Vertex cur = next(vIterator);

		// Invariant : Iterates through all vertices, reseting the properties of
		// each vertex accordingly.
		while (cur != null) {
			cur.parent = null;
			cur.seen = false;
			cur.count = 0;
			cur.distance.setInfinity();
			cur = next(vIterator);
		}
		// initializing source vertex by updating its distance to 0.
		src.distance.setValue(0);
	}

	/**
	 * Relaxes the vertex v, by comparing its distance with u.distance and edge
	 * e's weight.
	 * 
	 * @param u
	 *            : Vertex : input vertex 1
	 * @param v
	 *            : Vertex : vertex which will be relaxed
	 * @param e
	 *            : Edge : edge which is being added in relaxation.
	 * @return : boolean : true if the relaxation goes through, false otherwise
	 */
	static boolean relax(Vertex u, Vertex v, Edge e) {
		int eWeight = e.Weight;
		if (isGreater(v, u, eWeight)) {
			v.parent = u;
			v.distance.setValue(u.distance.value + eWeight);
			return true;
		}
		return false;

	}

	/**
	 * Returns true if vertex v distance is greater than u's distance combined
	 * with edgeWeight. This is used in relaxation of Vertex v.
	 * 
	 * @param v
	 *            : Vertex : input vertex
	 * @param u
	 *            : Vertex : input vertex 2
	 * @param edgeWeight
	 *            : int : edge weight for relaxation
	 * @return : boolean : true if v is greater than u + edgeWeight
	 */
	static boolean isGreater(Vertex v, Vertex u, int edgeWeight) {
		if(u.distance.isInfinity && v.distance.isInfinity)
			return false;
		if (v.distance.isInfinity )
			return true;
		if(u.distance.isInfinity)
			return false;
		return v.distance.value > (u.distance.value + edgeWeight);
	}

	/**
	 * Prints the output using the graph and shortest path weights updated in
	 * each vertex.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @param algo
	 *            : AlgoType : tells which algorithm was implemented to find the
	 *            shortest paths.
	 */
	static void printOutput(Graph g) {
		AlgoType algo = g.algo;
		Iterator<Vertex> vIterator = g.iterator();
		Vertex cur = next(vIterator);
		if (algo == AlgoType.B_F)
			System.out.print(BFALGO + SPACE + g.spWeight);
		else
			System.out.print(algo + SPACE + g.spWeight);

		if (g.numNodes <= 100)

			// Invariant : Iterates through all vertices, printing its weight
			// and
			// its parent. If the parent is null, it can either be source or
			// vertex
			// which is not reachable from source. as distance to source from
			// source
			// is 0, this check is used to differentiate between source and
			// non-reachable vertices.
			while (cur != null) {
				System.out.println();
				System.out.print(cur.name + SPACE);
				if (cur.parent == null && !cur.distance.isInfinity)
					System.out.print(cur.distance.value + SPACE + HYPEN);
				else if (cur.parent == null && cur.distance.isInfinity)
					System.out.print(INFINITY + SPACE + HYPEN);
				else
					System.out.print(cur.distance.value + SPACE
							+ cur.parent.name);
				cur = next(vIterator);
			}

	}

	/**
	 * Returns next Vertex starting from source. This is used to iterate
	 * topologically sorted vertices from source, vertices before source are
	 * ignored as they are not reachable from source.
	 * 
	 * @param iterator
	 *            : Iterator<Vertex> : vertex iterator
	 * @param src
	 *            : Vertex : source Vertex
	 * @param found
	 *            : boolean : flag to tell if source is already found
	 * @return : Vertex : vertex if available, null otherwise
	 */
	static Vertex getNextTopSorted(Iterator<Vertex> iterator, Vertex src,
			boolean found) {
		if (found) {
			if (iterator.hasNext())
				return iterator.next();
			else
				return null;
		} else {
			while (iterator.hasNext()) {
				Vertex temp = iterator.next();
				if (temp.equals(src))
					return temp;
			}
			return null;
		}
	}

	/**
	 * Returns true if the edge can be on the shortest path to v. i.e. if the
	 * sum of distance to u and edge e weight are equal to distance of v from
	 * source, means edge e can be part of shortest path to v.
	 * 
	 * @param u
	 *            : Vertex : from vertex of edge to be verified
	 * @param v
	 *            : Vertex : to vertex of edge to be verified for
	 * @param e
	 *            : Edge : which is being checked
	 * @return : boolean : true if edge can be part of shortest path to v, false
	 *         otherwise
	 */
	public static boolean isEqual(Vertex u, Vertex v, Edge e) {
		if (u.distance.isInfinity || v.distance.isInfinity)
			return false;
		if (u.distance.value + e.Weight == v.distance.value)
			return true;
		return false;
	}

	/**
	 * Returns the next unprocessed vertex (which is not processed for
	 * calculating all shortest paths)
	 * 
	 * @param iterator
	 *            : Iterator<Vertex> : iterator of vertex
	 * @return : Vertex : next unprocessed vertex if present, null otherwise
	 */
	public static Vertex getUnprocessedVertex(Iterator<Vertex> iterator) {
		while (iterator.hasNext()) {
			Vertex v = iterator.next();
			if (!v.isSPCalculated)
				return v;
		}
		return null;
	}

	/**
	 * Returns the vertex using the edge iterator which is part of shortest
	 * paths.
	 * 
	 * @param iterator
	 *            : Iterator<Edge> : edge iterator
	 * @return : Vertex : if there are any eligible edges, returns the Vertex
	 *         from them, null otherwise
	 */
	public static Vertex getIncidentVertex(Iterator<Edge> iterator) {
		Edge e = getEligibleEdge(iterator);
		Vertex from = null;
		if (e != null)
			from = e.From;
		return from;
	}

	/**
	 * Outputs the graph with vertex, its shortest path weight, num of shortest
	 * paths - with exception to first line where it just prints the algorithm
	 * used and sum of num of shortest paths from source to every other vertex.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	public static void printL2Output(Graph g) {
		Iterator<Vertex> iterator = g.iterator();
		Vertex cur = next(iterator);
			System.out.print( g.totalPaths);
		if (g.numNodes <= 100)
			// Invariant : Iterates through all vertices, printing its weight
			// and
			// no of shortest paths. If the vertex is not reachable from source,
			// the weight will be INF and total paths will be 0.
			while (cur != null) {
				System.out.println();
				System.out.print(cur.name);
				System.out.print(SPACE);
				if (cur.distance.isInfinity)
					System.out.print(INFINITY);
				else
					System.out.print(cur.distance.value);
				System.out.print(SPACE);
				System.out.print(cur.spCount);
				cur = next(iterator);
			}
	}

	/**
	 * Resets the degree of all vertices to 0 of the given graph.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	public static void resetDegree(Graph g) {
		Iterator<Vertex> iterator = g.iterator();
		Vertex cur = next(iterator);

		// Invariant : cur is used to hold the vertex in each iteration for
		// which we are resetting the degree field. This resetting is used to
		// calculate topological order again.
		while (cur != null) {
			cur.degree = 0;
			cur = next(iterator);
		}
	}

	/**
	 * Returns an edge which is eligible, by eligible it means that edge which
	 * can be part of any shortest path.
	 * 
	 * @param iterator
	 *            : Iterator<Edge> : iterator for edge
	 * @return : Edge : eligible edge if present, null otherwise
	 */
	public static Edge getEligibleEdge(Iterator<Edge> iterator) {
		while (iterator.hasNext()) {
			Edge e = iterator.next();
			if (!e.isEligible)
				continue;
			return e;
		}
		return null;
	}
	
	/**
	 * Prints the cycle using the vertex, it iterates back from the
	 * vertex till it finds already visited vertex.
	 * 
	 * @param v
	 *            : Vertex : input vertex
	 */
	static void printGraphCycle(Vertex v) {
		while (v != null) {
			if (v.forCycle) 
				break;
			v.forCycle = true;
			System.out.print(v.name);
			System.out.print(Helper.SPACE);
			System.out.print(v.distance.value);
			System.out.print(Helper.SPACE);
			v = v.parent;
			if(v != null)
				System.out.println(v.name);
		}
	}
	
	/**
	 * Returns the list of edges forming the cycle.
	 * @param v : Vertex : vertex on the cycle
	 * @return : List<Edge> : list of edges forming cycle
	 */
	static List<Edge> findCycleEdges(Vertex v){
		List<Edge> cycEdges = new ArrayList<Edge>();
		while(v != null){
			Edge cycE = findIncidentEdge(v, v.parent);
			if(cycE != null)
				cycEdges.add(cycE);
			if(v.forCycle)
				break;
			v.forCycle = true;
			v = v.parent;
		}
		return cycEdges;
	}
	
	/**
	 * Returns the edge (u,v), using v iterating on reverese adjacency list of v
	 * @param v : Vertex : input vertex
	 * @param u : Vertex : vertex from which the edge should be to v
	 * @return : Edge : edge u,v
	 */
	static Edge findIncidentEdge(Vertex v, Vertex u){
		if(u == null)
			return null;
		for(Edge e : v.revAdj){
			if(e.From == u)
				return e;
		}
		return null;
	}
	
	/**
	 * Prints the list of edges
	 * @param edges : List<Edge> : list of edges
	 */
	static void printEdges(List<Edge> edges){
		if(edges == null)
			return ;
		for(Edge e : edges)
			System.out.println(e);
	}
}
