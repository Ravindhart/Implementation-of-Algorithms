/**
 * class for finding the shortest path from source vertex using methods as
 * applicable.
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 10:59 pm
 */
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class ShortestPaths {

	/**
	 * Finds and prints the shortest paths from source to every other vertex.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	public static void findShortestPath(Graph g) {
		if (g == null)
			System.out.println("No Graph");

		Vertex cVertex = getShortestPaths(g);
		if (cVertex != null)
			System.out
					.println("Unable to solve problem. Graph has a negative cycle");
		else
			Helper.printOutput(g);
	}

	/**
	 * Finds the shortest paths from source to all vertices, if there is a
	 * negative cycle it returns false indicating the same.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : Vertex : vertex involving in negative cycle if there is one,
	 *         null otherwise.
	 */
	static Vertex getShortestPaths(Graph g) {

		Vertex cycVertex = null;
		// processGraph tells which algorithm to implement for the input graph
		processGraph(g);
		AlgoType algo = g.algo;
		switch (algo) {
		case BFS:
			useBFS(g);
			break;
		case DAG:
			useDAG(g);
			break;
		case Dij:
			useDijkstra(g);
			break;
		case B_F:
			cycVertex = bellmanFord(g);
			break;
		}
		return cycVertex;
	}

	/**
	 * Returns the Enum with appropraite algorithm name which can be implemented
	 * for the input graph. BFS - if all edges have same uniform weights, DAG -
	 * if the graph is acyclic, Dij (represents Dijkstra's) - if all edge
	 * weights are positive, else B_F (represents Bellman Ford), preference in
	 * left-to-right order
	 * 
	 * @param g
	 *            : Graph : input graph.
	 */
	static void processGraph(Graph g) {
		AlgoType algo;
		List<Edge> edges = g.edges;
		Iterator<Edge> eIterator = edges.iterator();
		Edge cur = Helper.next(eIterator);
		int eWeight = cur.Weight;
		boolean isNeg = false;
		boolean isBFS = true;
		// Invariant: cur holds the edge in each iteration, loop checks if the
		// edge weights are all unique or if they are negative.
		while (cur != null) {
			// isBFS is just used to avoid cur.Weight != eWeight condition check
			// if any one of the weight is not same as eWeight.
			if (isBFS && cur.Weight != eWeight)
				isBFS = false;
			if (cur.Weight < 0) {
				isNeg = true;
				break;
			}
			cur = Helper.next(eIterator);
		}

		// if some weights are negative, the algo can be DAG or Bellman ford
		// depending on g being acyclic.
		if (isNeg)
			if (isCyclic(g))
				algo = AlgoType.B_F;
			else
				algo = AlgoType.DAG;
		else if (isBFS) // straight case
			algo = AlgoType.BFS;
		// if the graph does not have any negative weights but are not different
		// weights, then algo can be DAG or Bellman Ford depending on graph
		// being acyclic
		else {
			if (isCyclic(g))
				algo = AlgoType.Dij;
			else
				algo = AlgoType.DAG;
		}
		g.algo = algo;
	}

	/**
	 * Returns true if the input graph has a cycle, false otherwise.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : boolean : true if the graph has a cycle, false otherwise.
	 */
	static boolean isCyclic(Graph g) {
		List<Vertex> topSorted = toplogicalOrder(g);
		if (topSorted.size() != g.numNodes)
			return true;
		return false;
	}

	/**
	 * Performs BFS on the input graph, accordingly updates the list of Vertex's
	 * with appropriate distance and parent values.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	public static void useBFS(Graph g) {

		// Initialization done while creating a graph.
		// Initialization includes for all u belongs to V, u.parent = null,
		// u.seen = false, u.distance to Infinity
		Vertex src = g.verts.get(1);
		src.seen = true;
		src.distance.setValue(0);
		Queue<Vertex> vertQ = new LinkedList<Vertex>();
		vertQ.add(src);
		int spWeight = 0;
		int eWeight = g.edges.get(1).Weight;
		// Invariant: vertQ holds the queue of vertices, representing the
		// vertices which are already seen; in each iteration, we remove one
		// vertex from this queue and update the distance of its adjacent
		// vertices which are not seen and add the same vertex to the
		// queue. We are not verifying for cycle's as it has been
		// verified in pre-processing and this method is triggered with graph
		// only if its applicable
		while (!vertQ.isEmpty()) {
			Vertex cur = vertQ.poll();
			// Invariant : for each adjacent vertex which is already not seen,
			// we update the distance of the adjacent as cur.distance + uniform
			// weight
			for (Edge e : cur.Adj) {
				Vertex v = e.otherEnd(cur);
				if (!v.seen) {
					v.distance.setValue(cur.distance.value + eWeight);
					spWeight = spWeight + cur.distance.value + eWeight;
					v.seen = true;
					v.parent = cur;
					vertQ.add(v);
				}
			}
		}
		g.spWeight = spWeight;
	}

	/**
	 * Implements DAG shortest path algorithm on the given graph. It initially
	 * gets the list of vertices in topologically sorted order, and starts
	 * processing from source, anything before source is not reachable from
	 * source.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	public static void useDAG(Graph g) {
		Helper.resetGraph(g);
		// list of vertex in topological order
		List<Vertex> topSorted = toplogicalOrder(g);
		Vertex src = g.verts.get(1);
		Helper.initialize(g, src);
		Iterator<Vertex> tsIterator = topSorted.iterator();

		// gets next available vertex starting from Source in topological
		// ordered list.
		Vertex cur = Helper.getNextTopSorted(tsIterator, src, false);
		int spWeight = 0;

		int temp;
		// Invariant : cur represents the vertex which is being processed in
		// each iteration; cur is processed by relaxing all its adjacent
		// vertices. Here temp is used a temporary variable for calculating the
		// sum of shortest path, it is used to store earlier weight of vertex,
		// incase if this vertex gets relaxed we have to remove that amount of
		// weight from total sum
		while (cur != null) {
			for (Edge e : cur.Adj) {
				Vertex v = e.otherEnd(cur);
				// temp = getEarlierWeight(v);
				temp = v.distance.value;
				if (Helper.relax(cur, v, e))
					spWeight = spWeight + cur.distance.value + e.Weight - temp;

			}
			cur = Helper.getNextTopSorted(tsIterator, src, true);
		}
		g.spWeight = spWeight;
	}

	/**
	 * Runs Dijkstra's shortest path algorithm, It starts by creating an indexed
	 * heap of all vertices and in each iteration, we take the head of the heap
	 * and process (relaxation) its adjacency list (if not already processed)
	 * and as relaxation makes changes to vertex distance, the indexed heap is
	 * updated for reflecting the change.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	public static void useDijkstra(Graph g) {
		List<Vertex> vertexList = g.verts;
		Vertex src = vertexList.get(1);
		Vertex[] vertices = new Vertex[vertexList.size()];
		vertices = vertexList.toArray(vertices);
		Helper.initialize(g, src);
		IndexedHeap<Vertex> vertexHeap = new IndexedHeap<Vertex>(vertices,
				vertices[1]);
		int spWeight = 0;
		// Invariant: vertexHeap represents the indexed heap of vertex's, in
		// each iteration we take the minimum vertex (head of the heap) and
		// relax its adjacent vertices (if not processed in earlier iterations)
		// and accordingly update the indexed heap by calling decreaseKey method
		// of indexed heap.
		while (!vertexHeap.isEmpty()) {
			Vertex cur = vertexHeap.remove();
			cur.seen = true;
			spWeight = spWeight + cur.distance.value;
			for (Edge e : cur.Adj) {
				Vertex other = e.otherEnd(cur);
				if (Helper.isGreater(other, cur, e.Weight) && !other.seen) {
					Helper.relax(cur, other, e);
					vertexHeap.decreaseKey(other);
				}
			}
		}
		g.spWeight = spWeight;
	}

	/**
	 * Returns true if bellmanford has successfully executed without any
	 * negative cycles, returns false if its finds any negative cycles in the
	 * input graph.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : Vertex : vertex, if negative cycle found, null if successfully
	 *         executes bellman ford algorithm
	 */
	public static Vertex bellmanFord(Graph g) {
		int vertCount = g.numNodes;
		Vertex src = g.verts.get(1);
		Helper.initialize(g, src);
		Queue<Vertex> vertexQ = new LinkedList<Vertex>();
		src.seen = true;
		vertexQ.add(src);
		int spWeight = 0;
		int temp;

		// Invariant : vertexQ is a queue of vertices which are to be processed;
		// in each iteration we take a vertex from this queue and if its not
		// already processed for node count times (which represents negative
		// cycle), we take each of its adjacent Vertex and update its distance
		// based on the current vertex distance.
		while (!vertexQ.isEmpty()) {
			Vertex cur = vertexQ.poll();
			cur.seen = false;
			cur.count++;
			if (cur.count >= vertCount)
				return cur;
			for (Edge e : cur.Adj) {
				Vertex other = e.otherEnd(cur);
				int eWeight = e.Weight;
				if (Helper.isGreater(other, cur, eWeight)) {
					// temp = getEarlierWeight(other);
					temp = other.distance.value;
					other.distance.setValue(cur.distance.value + eWeight);
					other.parent = cur;
					spWeight = spWeight + cur.distance.value + eWeight - temp;
					if (!other.seen) {
						vertexQ.add(other);
						other.seen = true;
					}
				}
			}
		}
		g.spWeight = spWeight;
		return null;
	}

	/**
	 * Returns the list of Vertices in Topological order. Algorithm implemented
	 * is to begin with vertices (with degree 0, i.e. no incoming edges),
	 * enqueue the vertex. Next, for each vertex in queue, remove the vertex and
	 * update the degree of all adjacent vertices, also adding any adjacent
	 * vertex with degree 0 to Queue and repeating the process.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : List<Vertex> : list of vertices in topologically sorted order
	 */
	static List<Vertex> toplogicalOrder(Graph g) {
		int vSize = g.numNodes;
		Iterator<Vertex> vertexIterator = g.iterator();
		Queue<Vertex> vQueue = new LinkedList<Vertex>();
		Vertex v = Helper.next(vertexIterator);

		// Invariant: v is to hold the vertex as we iterate over all vertices,
		// checking for degree being 0. If found, we add the vertex to Queue.
		while (v != null) {
			if (v.degree == 0)
				vQueue.add(v);
			v = Helper.next(vertexIterator);
		}

		List<Vertex> topSorted = new ArrayList<Vertex>();

		// Invariant: for each vertex in vQueue (which is holding all vertices
		// with in-degree as 0), we remove the vertex (simulated by reducing the
		// degree of adjacent vertices) and check if there is any vertex with
		// degree 0 to enqueue it into vQueue, and update the topological order
		// of already processed vertex.
		while (!vQueue.isEmpty() && vSize > topSorted.size()) {
			Vertex from = vQueue.poll();
			from.seen = true;
			topSorted.add(from);
			for (Edge e : from.Adj) {
				Vertex to = e.otherEnd(from);
				to.degree--;
				if (!to.seen && to.degree == 0)
					vQueue.add(to);
			}
		}
		return topSorted;
	}

	/**
	 * Returns the weight of the vertex, if the weight is infinity return 0.
	 * 
	 * @param u
	 *            : Vertex : input vertex
	 * @return : int : weight of the vertex
	 */
	static int getEarlierWeight(Vertex u) {
		if (!u.distance.isInfinity)
			return u.distance.value;
		return 0;
	}

	/**
	 * Finds all the shortest paths from source to every other vertex.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	static void findAllShortestPaths(Graph g) {
		Vertex cVertex = getShortestPaths(g);
		if (cVertex != null) {
			System.out.println(Helper.CYC_MESSAGE);
			List<Edge> cycEdges = Helper.findCycleEdges(cVertex);
			Helper.printEdges(cycEdges);
		} else {
			Helper.resetDegree(g);
			addEligibleEdges(g);
			Vertex cycV = dfsForCycle(g);
			if (cycV != null) {
				System.out.println(Helper.CYC_MESSAGE);
				List<Edge> cycEdges = Helper.findCycleEdges(cycV);
				Helper.printEdges(cycEdges);
			} else {
				// List<Vertex> topSorted = findMTopologicalOrder(g);
				// findAllPaths(g, topSorted);
				findAllPaths(g);
				Helper.printL2Output(g);
			}
		}
	}

	/**
	 * Prints the negative cycle using the vertex, it iterates back from the
	 * vertex it gets from bellman ford till it finds already visited vertex.
	 * 
	 * @param v
	 *            : Vertex : input vertex
	 */
	static void printGraphCycle(Vertex v) {
		while (v != null) {
			if (v.forCycle) {
				System.out.print(v.name);
				break;
			}
			v.forCycle = true;
			System.out.print(v.name);
			System.out.print(Helper.LESSER);
			System.out.print(Helper.HYPEN);
			v = v.parent;
		}
	}

	/**
	 * Uses the iterator from the graph to find all shortest paths from source
	 * to each vertex. It uses the fact that sum of shortest paths to a vertex
	 * are equal to the sum of all shortest paths of all those vertices which
	 * have an eligible edge to the current vertex.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	static void findAllPaths(Graph g) {
		Vertex source = g.verts.get(1);
		source.spCount = 1;
		g.totalPaths = 1;
		source.isSPCalculated = true;
		Iterator<Vertex> vIerator = g.iterator();
		Vertex cur = Helper.getUnprocessedVertex(vIerator);
		// Invariant : cur holds the vertex which has not yet been processed
		// for shortest paths, We take such a vertex and call findAllPaths
		// which finds all the shortest paths for that vertex from source
		// and updates the same in the vertex (if applicable)
		while (cur != null) {
			findAllPaths(cur, g);
			cur = Helper.getUnprocessedVertex(vIerator);
		}
	}

	/**
	 * Finds all eligible paths for the given vertex from source
	 * 
	 * @param u
	 *            : Vertex : input vertex
	 *            
	 * @param g : Graph : input graph, this is just added to update the totalShortest path count in graph.
	 */
	static void findAllPaths(Vertex u, Graph g) {
		int cCount = 0;

		// If the vertex is not reachable from source, the number of shortest
		// paths applicable will be zero and can be ignored from calculating any
		// further on this vertex
		if (u.distance.isInfinity) {
			u.spCount = 0;
			u.isSPCalculated = true;
			return;
		}
		Iterator<Edge> eIterator = u.revAdj.iterator();
		Vertex cur = Helper.getIncidentVertex(eIterator);

		// Invariant: Logic implemented is # of shortest paths to a vertex u is
		// equal to sum of all shortest paths of vertices which have an eligible
		// edge to the vertex (u in this case), of such vertices, we take each
		// vertex and process it. cur represents Vertex being processed for its
		// shortest paths, the vertex is initially checked if the shortest paths
		// are already calculated, if so we use those values, if not we
		// recursively calculate the shortest paths for each of such vertex.
		while (cur != null) {
			if (cur.isSPCalculated)
				cCount = cCount + cur.spCount;
			else{
				findAllPaths(cur, g);
				cCount = cCount + cur.spCount;
			}
			cur = Helper.getIncidentVertex(eIterator);
		}
		u.isSPCalculated = true;
		u.spCount = cCount;
		g.totalPaths = g.totalPaths + cCount;
	}

	static void copyVertices(List<Vertex> fromV, List<Vertex> toV) {
		int size = fromV.size();
		int i;
		for (i = 1; i < size; i++) {
			Vertex from = fromV.get(i);
			Vertex to = toV.get(i);
			to.distance.fill(from.distance);
			int temp = from.parent.name;
			to.parent = toV.get(temp);
		}
	}

	/**
	 * Updates all edges which are part of any shortest paths to vertices which
	 * are incident on them.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	static void addEligibleEdges(Graph g) {
		List<Edge> edges = g.edges;

		// Invariant : Iterates through all edges and checks for the condition
		// from.distance + edge.Weight == to.distance in which case this edge
		// can be a part of shortest path to vertex to.
		for (Edge edge : edges) {
			Vertex to = edge.To;
			Vertex from = edge.From;
			if (Helper.isEqual(from, to, edge)) {
				edge.isEligible = true;
				to.degree++;
			}
		}
	}

	/**
	 * Returns list of Vertices in topological order, it differs from the
	 * original topologicalOrder method as it considers only eligible edges
	 * while iterating.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : List<Vertex> : topological sorted vertices
	 */
	static List<Vertex> findMTopologicalOrder(Graph g) {

		int vSize = g.numNodes;
		Iterator<Vertex> vertexIterator = g.iterator();
		Queue<Vertex> vQueue = new LinkedList<Vertex>();
		Vertex v = Helper.next(vertexIterator);

		// Invariant: v is to hold the vertex as we iterate over all vertices,
		// checking for degree being 0. If found, we add the vertex to Queue.
		while (v != null) {
			if (v.degree == 0)
				vQueue.add(v);
			v = Helper.next(vertexIterator);
		}

		List<Vertex> topSorted = new ArrayList<Vertex>();

		// Invariant: for each vertex in vQueue (which is holding all vertices
		// with in-degree as 0), we remove the vertex (simulated by reducing the
		// degree of adjacent vertices) and check if there is any vertex with
		// degree 0 to enqueue it into vQueue, and update the topological order
		// of already processed vertex.
		while (!vQueue.isEmpty() && vSize > topSorted.size()) {
			Vertex from = vQueue.poll();
			from.seen = true;
			topSorted.add(from);
			Iterator<Edge> iterator = from.Adj.iterator();
			Edge e = Helper.getEligibleEdge(iterator);
			while (e != null) {
				Vertex to = e.otherEnd(from);
				to.degree--;
				if (!to.seen && to.degree == 0)
					vQueue.add(to);
				e = Helper.getEligibleEdge(iterator);
			}
		}
		return topSorted;
	}

	/**
	 * Performs DFS on the input graph, this is specifically used to output the
	 * cycle in a graph
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : Vertex : vertex which is a part of any cycle
	 */
	static Vertex dfsForCycle(Graph g) {
		Iterator<Vertex> iterator = g.iterator();
		Vertex cur = Helper.next(iterator);
		Vertex cycVertex = null;

		// Invariant : cur - holds the reference to each vertex using the
		// iterator. Inside the loop, we check if the current vertex is not
		// visited, if so we perform DFS.
		while (cur != null) {
			if (cur.visit == VisitStatus.WHITE) {
				cycVertex = dfsVisit(cur);
				if (cycVertex != null)
					break;
			}
			cur = Helper.next(iterator);
		}
		return cycVertex;
	}

	/**
	 * Performs dfs visit for the vertex.
	 * 
	 * @param u
	 *            : Vertex : input vertex
	 * @return : Vertex : if there is a cycle, it returns a vertex which is part
	 *         of that cycle; null otherwise
	 */
	static Vertex dfsVisit(Vertex u) {
		u.visit = VisitStatus.GREY;
		Iterator<Edge> iterator = u.Adj.iterator();
		Edge e = Helper.getEligibleEdge(iterator);
		Vertex t = null;

		// Invariant: e holds the reference to the edge which is eligible and
		// adjacent to the vertex on which we are performing DFS, checks if the
		// vertex if already in 'GREY - being processed' state, if so it
		// indicates that the vertex is part of the cycle and returns the same,
		// if not recursively processes the vertex for DFS
		while (e != null) {
			Vertex v = e.otherEnd(u);
			if (v.visit == VisitStatus.GREY) {
				v.parent = u;
				return v;
			}
			if (v.visit == VisitStatus.WHITE) {
				v.parent = u;
				t = dfsVisit(v);
				if (t != null)
					return t;
			}
			e = Helper.getEligibleEdge(iterator);
		}
		u.visit = VisitStatus.BLACK;
		return t;
	}
}
