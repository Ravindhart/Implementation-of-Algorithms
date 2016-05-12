import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Assumptions: Graph is undirected.
 * For directed, minor tweaks are necessary in verifyGraph function to find odd vertex
 */

/**
 * class for checking euler property of a graph (incl. tour, path).
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
 *
 */
public class EulerUtil {

	/**
	 * Returns list of edges forming euler tour.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : DoublyLinkedList<Edge> : doubly linked list of edges forming
	 *         euler path
	 */
	static DoublyLinkedList<Edge> findTour(Graph g) {

		/**
		 * This method is called when the input graph g has euler tour, which
		 * means we can start from any vertex to visit all the edges exactly
		 * once, so we take the first vertex from the graph and use method
		 * findEulerPath to find the circuit
		 */
		Vertex src = g.verts.get(1);
		return findEulerPath(g, src);
	}

	/**
	 * Returns an euler path in the graph g starting from vertex start.
	 * 
	 * @param g
	 *            : Graph
	 * @param start
	 *            : Vertex : starting vertex for the path
	 * @return : DoublyLinkedList<Edge> : doubly linked list of edges forming
	 *         euler path/tour
	 */
	static DoublyLinkedList<Edge> findEulerPath(Graph g, Vertex start) {

		/**
		 * This method is called to find the euler path/tour(if exists) from the
		 * start vertex, if the starting vertex has odd degree then this will be
		 * euler path. Logic : Takes the vertex and stacks if there are
		 * unprocessed edges on the vertex and in the iteration we take each
		 * vertex to find the circuit and in the process we stack more vertex's
		 * as we encounter.
		 */
		Stack<Vertex> unusedEdgesV = new Stack<Vertex>();
		unusedEdgesV.push(start);
		DoublyLinkedList<Edge> baseList = new DoublyLinkedList<Edge>();
		DoublyLinkedList<Edge> childList;

		// Invariant : unusedEdgesV stack is used to stack vertex's which still
		// have some unprocessed edges, for each iteration we take one such
		// vertex, find the circuit and merge the loop returned by findCircuit
		// with baseList which has already merged loops.
		while (!unusedEdgesV.isEmpty()) {
			Vertex u = unusedEdgesV.pop();
			if (u.degree > 0) {
				childList = findCircuit(g, u, unusedEdgesV);
				mergeLists(baseList, childList, u);
			}
		}
		return baseList;
	}

	/**
	 * Merges the childList to the baseList for the source vertex src.
	 * 
	 * @param baseList
	 *            : DoublyLinkedList<Edge> : already merged list of edges
	 *            forming circuits
	 * @param childList
	 *            : DoublyLinkedList<Edge> : new circuit (list of edges) which
	 *            has to be merged to the already merged list
	 * @param src
	 *            : Vertex : vertex from which the new loop/circuit is formed
	 */
	static void mergeLists(DoublyLinkedList<Edge> baseList,
			DoublyLinkedList<Edge> childList, Vertex src) {
		/**
		 * Merges the childList to the baseList(if not empty), uses the index
		 * stored in Vertex to get the location and uses it merge eg. baseList:
		 * (1,2) <-> (2,4) <-> (4,1), child list: (2,3)<->(3,6)<->(6,2) merged:
		 * (1,2) <->(2,3)<->(3,6)<->(6,2)<-> (2,4)<->(4,1) Note: if baseList is
		 * empty, childList is as it is copied to baseList
		 */
		if (baseList.tail == null) {
			baseList.header = childList.header;
			baseList.tail = childList.tail;
			baseList.size = childList.size;
		} else {
			Entry<Edge> index = src.getIndex();
			Entry<Edge> prev = index.prev;
			if (childList.header.next != null) {
				prev.next = childList.header.next;
				childList.tail.next = index;
				index.prev = childList.tail;
			}
			baseList.size = baseList.size + childList.size;
		}
	}

	/**
	 * Finds the Circuit in the graph from the src vertex, stacks the vertex to
	 * unusedEdgesV if it has any unused edges.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @param src
	 *            : Vertex : source vertex from which the circuit has to be
	 *            formed
	 * @param unusedEdgesV
	 *            : Stack<Vertex> : stack to store Vertex which has unused edges
	 * @return : DoublyLinkedList<Edge> : list of edges forming circuit (or
	 *         path)
	 */
	static DoublyLinkedList<Edge> findCircuit(Graph g, Vertex src,
			Stack<Vertex> unusedEdgesV) {
		DoublyLinkedList<Edge> dList = new DoublyLinkedList<Edge>();
		Edge e = Helper.getEdge(src);
		if (src.degree > 0)
			unusedEdgesV.push(src);
		Vertex u, v;
		u = src;

		// Invariant: e refers to an unprocessed edge from the vertex u, we save
		// this edge to the output list (also note the address saved in list to
		// vertex), and take the other end of the edge and continue the process.
		// Each time we update e to an unprocessed edge from vertex u (which is
		// updated in each iteration to otherend of e), e being null represents
		// there are no more edges to be processed and we can halt the loop
		while (e != null) {
			e.visited = true;
			Entry<Edge> index = dList.add(e);
			if (u.index == null)
				u.putIndex(index);
			v = e.otherEnd(u);
			e = Helper.getEdge(v);
			if (v.degree > 0)
				unusedEdgesV.push(v);
			u = v;
		}
		return dList;
	}

	/**
	 * Pre Condition : Graph is undirected. 
	 * Returns an EulerBean
	 * obj representing if the graph has either euler tour or path or none.
	 * 
	 * @param g
	 *            : Graph : input graph
	 */
	static EulerBean verifyGraph(Graph g) {
		EulerBean returnBean;
		if(!isConnected(g)){
			returnBean = new EulerBean(3, null);
			return returnBean;
		}
		Iterator<Vertex> vIterator = g.iterator();
		Vertex current = Helper.next(vIterator);
		int oddDegreeVertCount = 0;
		List<Vertex> oddDegVertexList = new ArrayList<Vertex>();

		// Invariant: current holds the reference to the currently processed
		// vertex (iterates over the graph for each vertex), for each vertex we
		// check for the even degree and maintain the count of number of odd
		// degree vertices in oddDegreeVertCount, we even hold the reference to
		// two odd vertices in oddDegVertexList - if the count of odd degree
		// vertices is just two, we use this list to state the eulerian path.
		while (current != null) {
			if (current.degree % 2 != 0) {
				oddDegreeVertCount++;
				if (oddDegreeVertCount <= 2)
					oddDegVertexList.add(current);
			}
			current = Helper.next(vIterator);
		}

		// Invariant: Creats a returnEulerBean, if the input graph has euler
		// tour, returnbean will have 1 as eulerTourOrPath (2 represents euler
		// path, 3 represents none), if the input graph has euler path, return
		// bean will have vertex with odd degree.
		if (oddDegreeVertCount == 0)
			returnBean = new EulerBean(1, null);
		else if (oddDegreeVertCount == 2)
			returnBean = new EulerBean(2, findSmallVertex(
					oddDegVertexList.get(0), oddDegVertexList.get(1)));
		else
			returnBean = new EulerBean(3, null);
		return returnBean;
	}

	/**
	 * Returns the smallest of two vertices.
	 * 
	 * @param u
	 *            : Vertex : input vertex 1
	 * @param v
	 *            : Vertex : input vertex 2
	 * @return : Vertex : smallest of both vertices
	 */
	static Vertex findSmallVertex(Vertex u, Vertex v) {
		int val = u.compareTo(v);
		if (val > 0)
			return v;
		else
			return u;

	}

	/**
	 * Returns a list of edges which forms either euler path or euler tour, null
	 * if none
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : DoublyLinkedList<Edge> : list of edges if there is path or
	 *         tour, null otherwise
	 */
	static DoublyLinkedList<Edge> findEulerTour(Graph g) {
		EulerBean graphProp = verifyGraph(g);
		if (graphProp.eulerTourOrPath == 3)
			return null;
		else if (graphProp.eulerTourOrPath == 1)
			return findTour(g);
		else
			return findEulerPath(g, graphProp.oddVertex);

	}
	
	/**
	 * Returns true if the given graph is connected, false otherwise.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : boolean : true if the given graph is connected, false otherwise
	 */
	static boolean isConnected(Graph g) {
		return bfsCheck(g);
	}

	/**
	 * Returns true if the BFS processes all vertices proving the graph is
	 * connected i.e. implements BFS to check if the given graph is connected.
	 * 
	 * @param g
	 *            : Graph : input graph
	 * @return : boolean : true if the graph is connected, false otherwise
	 */
	static boolean bfsCheck(Graph g) {
		Iterator<Vertex> vIterator = g.iterator();
		Vertex root = Helper.next(vIterator);
		Queue<Vertex> vQueue = new LinkedList<Vertex>();
		int vertexCount = 0;
		root.seen = true;

		vQueue.add(root);
		while (!vQueue.isEmpty()) {
			Vertex u = vQueue.poll();
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (!v.seen) {
					v.seen = true;
					vQueue.add(v);
				}
			}
			vertexCount++;
		}
		// checking the count of vertex processed is same as total number of
		// vertices in graph
		return vertexCount == g.numNodes;
	}
	
	/**
	 * Returns true if the List of edges given forms a valid euler tour/path.
	 * @param dList : DoublyLinkedList<Edge> : list of edges
	 * @return : boolean : true if the given tour forms a valid euler tour
	 */
	static boolean verifyTour(DoublyLinkedList<Edge> dList){
		if (dList.tail == null)
			return false;
		
		Entry<Edge> prev = dList.header.next;
		Entry<Edge> current = prev.next;
		while(current != null){
			if(!areAdjEdges(prev.element, current.element) && !isValidVisit(prev.element))
				return false;
			prev = current;
			current = current.next;
		}
		if(!prev.element.visited)
			return false;
		return true;
	}
	
	/**
	 * Returns true if both the edges have atleast one common vertex.
	 * @param e1 : Edge 1
	 * @param e2 : Edge 2
	 * @return: boolean : true if edges have one common vertex else false
	 */
	static boolean areAdjEdges(Edge e1, Edge e2){
		if(e1.compareTo(e2) == 0)
			return true;
		else return false;
	}
	
	/**
	 * Returns true if the edge is visited only once in this trial
	 * @param e : Edge : input edge
	 * @return : true if valid visit, false other wise
	 */
	static boolean isValidVisit(Edge e){
      if(e.visited){
    	  e.visited = false;
    	  return true;
      }
      else{
    	  return false;
      }

	}
	
}

/*Usage: EulerUtil.findEulerTour(g)
sample input - tour
6 10
1 2 1
1 3 1
1 4 1
1 6 1
2 3 1
3 6 1
3 4 1
4 5 1
4 6 1
5 6 1

Graph.readGraph is called to build a graph using above graph

sample output:
(1,6) 
(5,6) 
(4,5) 
(4,6) 
(3,6) 
(3,4) 
(1,4) 
(1,3) 
(2,3) 
(1,2) 

sample input - path
6 9
1 2 1
1 3 1
1 4 1
1 6 1
3 6 1
3 4 1
4 5 1
4 6 1
5 6 1
sample output:
(1,2) 
(1,6) 
(5,6) 
(4,5) 
(4,6) 
(3,6) 
(3,4) 
(1,4) 
(1,3)
*/