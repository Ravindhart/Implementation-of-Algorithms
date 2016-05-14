
/**
 * Class for performing maximum bipartite matching  
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
 *
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Matching {

	final static int xorVal = 3;

		/**
	 * performs maximum bipartite matching
	 * 
	 * @param g:
	 *            Graph: the input graph
	 * @return: int: returns the cardinality of the maximum bipartite matching
	 */
	static int maxMatchingBGraph(Graph g) {
		// classifying the nodes as outer and inner
		if(!categorizeNodes(g))
			return -1;
		int mSize = findMaximalMatching(g);
		boolean aug = false;
		while (true) {
			aug = false;
			Queue<Vertex> vQueue = new LinkedList<Vertex>();
			Iterator<Vertex> vIterator = g.iterator();
			Vertex cur = Helper.next(vIterator);
			// Identifying free outer nodes and adding them to the queue
			while (cur != null) {
				cur.seen = false;
				cur.parent = null;
				if (cur.mate == null && cur.type == 2) {
					cur.seen = true;
					cur.root = cur;
					vQueue.add(cur);
				}
				cur = Helper.next(vIterator);
			}
			// Constructing alternative forest
			while (!vQueue.isEmpty()) {
				Vertex u = vQueue.remove();
				if (u.root.mate != null)
					continue;
				for (Edge e : u.Adj) {
					Vertex v = e.otherEnd(u);
					if (!v.seen) {
						v.parent = u;
						v.root = u.root;
						v.seen = true;
						// If free inner node is found
						if (v.mate == null) {
							processAugPath(v);
							mSize++;
							aug = true;
							break;

						}
						// If matched inner node is found,
						// its outer matched node is added to the queue
						else {
							Vertex x = v.mate;
							x.parent = v;
							x.root = v.root;
							x.seen = true;
							vQueue.add(x);
						}
					}
				}
			}
			if (vQueue.isEmpty() && !aug)
				break;
		}
		return mSize;
	}


	/**
	 * finds the maximal matching for the given graph
	 * 
	 * @param g:
	 *            Graph: the input graph
	 * @return: int: returns cardinality of maximal matching
	 */
	static int findMaximalMatching(Graph g) {
		int mSize = 0;
		for (Edge e : g.edges) {
			Vertex u = e.From;
			Vertex v = e.To;
			// If two ends of a edge are free, add it to the matching
			if (u.mate == null && v.mate == null) {
				u.mate = v;
				v.mate = u;
				mSize++;
			}
		}
		return mSize;
	}

	/**
	 * Processing the augment path i.e. alternating the matching
	 * 
	 * @param u:
	 *            Vertex: the free inner node at which the path ends
	 */
	static void processAugPath(Vertex u) {
		Vertex p = u.parent;
		Vertex x = p.parent;
		u.mate = p;
		p.mate = u;
		// until the root is reached for the tree, alternate the matching
		while (x != null) {
			Vertex nmx = x.parent;
			Vertex y = nmx.parent;
			x.mate = nmx;
			nmx.mate = x;
			x = y;
		}
	}

	/**
	 * Performs the classification of vertices of the given graph
	 * 
	 * @param g:
	 *            Graph: input graph
	 * @return:
	 */
	static boolean categorizeNodes(Graph g) {
		Iterator<Vertex> vIterator = g.iterator();
		Vertex current = Helper.next(vIterator);
		while (current != null) {
			if (!current.seen && !bfsToMark(current))
				return false;
			current = Helper.next(vIterator);
		}
		return true;
	}
/**
 * Performs BFS on each connected component
 * @param u: Vertex : the starting vertex for the BFS
 * @return: boolean : returns false if there is odd length cycle
 *  otherwise returns true
 */
	static boolean bfsToMark(Vertex u) {
		Queue<Vertex> vQueue = new LinkedList<Vertex>();
		u.type = 2;
		vQueue.add(u);
		while (!vQueue.isEmpty()) {
			Vertex cur = vQueue.remove();
			cur.seen = true;
			int type = cur.type;
			for (Edge e : cur.Adj) {
				Vertex other = e.otherEnd(cur);
				if (!other.seen) {
					//condition to see whether two outer or two inner
					//nodes are connected
					if (other.type != 0 && other.type == type)
						return false;
					other.type = type ^ xorVal;
					vQueue.add(other);
				}
			}
		}
		return true;
	}
}
