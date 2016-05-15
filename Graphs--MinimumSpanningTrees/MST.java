import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * class for calculating the weight of minimum spanning tree of given graph.
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/15/2016 12:09 pm
 *
 */
public class MST {

	static final int Infinity = Integer.MAX_VALUE;

	/**
	 * Returns the weight of minimum spanning tree formed from the graph g
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : int : weight of MST
	 */
	static int PrimMST1(Graph g) {
		int totalVertices, processedVertices = 1;
		int wmst = 0;
		totalVertices = g.numNodes;
		Vertex src = g.verts.get(1);
		src.seen = true;
		PriorityQueue<Edge> edgePQ = new PriorityQueue<Edge>();
		addAdjacencyToQueue(edgePQ, src);

		// Invariant : edgePQ is a priority queue which holds the edges ordered
		// according to their weight, In each iteration, we remove the head of
		// the queue and if any of the vertices of the edge have not been added
		// to MST, we add them (update wmst which is holding the weight of MST),
		// we then add the adjacent edges of this vertex to the queue. Also we
		// maintain a count of already processed vertices (processedVertices)
		// which if, is equal to total vertices in the graph; implies we have
		// formed our MST and we can break from the loop, - Reason for not
		// putting it as a condition in while loop is to avoid processing
		// adjacency list of vertex if the count is already reached
		while (!edgePQ.isEmpty()) {

			Edge minEdge = edgePQ.remove();
			if (minEdge.From.seen && minEdge.To.seen)
				continue;
			Vertex u, v;
			if (minEdge.From.seen) {
				u = minEdge.From;
				v = minEdge.To;
			} else {
				u = minEdge.To;
				v = minEdge.From;
			}
			v.parent = u;
			wmst = wmst + minEdge.Weight;
			v.seen = true;
			processedVertices++;
			if (processedVertices >= totalVertices)
				break;
			addAdjacencyToQueue(edgePQ, v);
		}
		return wmst;
	}

	/**
	 * Adds the adjacency list of source (src) vertex to the priority queue pq
	 * 
	 * @param pq
	 *            : PriorityQueue<Edge> : priority queue which has to be updated
	 * @param src
	 *            : Vertex : vertex whose adjacency list has to be processed
	 */
	static void addAdjacencyToQueue(PriorityQueue<Edge> pq, Vertex src) {
		for (Edge e : src.Adj) {
			Vertex w = e.otherEnd(src);
			if (!w.seen)
				pq.add(e);
		}
	}

	/**
	 * Returns the weight of minimum spanning tree formed from the graph g
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : int : weight of mst
	 */
	static int PrimMST2(Graph g) {
		int wmst = 0;
		Vertex src = g.verts.get(1);
		/*
		 * for(Vertex v : g.verts){ v.parent = null; v.seen = false; v.distance
		 * = Infinity; }
		 */
		List<Vertex> vertexList = g.verts;
		Vertex[] vertices = new Vertex[g.verts.size()];
		vertices = vertexList.toArray(vertices);
		src.distance = 0;
		IndexedHeap<Vertex> indexedPQ = new IndexedHeap<Vertex>(vertices,
				vertices[1]);
		// Invariant : indexedPQ is an indexed priority queue which holds all
		// the vertices of the graph (with minimum distance as root). In each
		// iteration, we take the root of the indexedPQ, update the wmst (wmst
		// holds the weight of minimum spanning tree) using the distance field
		// of vertex. And process the adjacency list of the vertex, processing
		// includes assigning parent to the adjacent vertex and updating the
		// vertex distance if the same is greater than
		// adjacent edge weight, as it disturbs the heap property we use
		// percolateUp method to restore the heap property
		while (!indexedPQ.isEmpty()) {
			Vertex current = indexedPQ.remove();
			current.seen = true;
			wmst = wmst + current.distance;
			for (Edge e : current.Adj) {
				Vertex v = e.otherEnd(current);
				if (!v.seen && v.distance > e.Weight) {
					v.distance = e.Weight;
					v.parent = current;
					indexedPQ.percolateUp(v.getIndex());
				}
			}
		}
		return wmst;
	}

	/**
	 * Main function is used to test the functions of this class.
	 * @param args : String[] : input test file location [optional]
	 * @throws FileNotFoundException : throws exception if the location is incorrect/file does not exist
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		Graph g = Graph.readGraph(in, false);
		System.out.println(PrimMST2(g));
	}
}
