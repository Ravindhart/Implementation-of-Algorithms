import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * simple driver program for testing BinaryHeap, Heapsort and MST
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 11:35 pm
 *
 */
public class Driver {
	/**
	 * main method which reads input data from either System.in or file and
	 * prints the results of the test.
	 * 
	 * @param args
	 *            : input test file location if any
	 * @throws FileNotFoundException
	 *             : Exception if the file name given is not present
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Please uncomment below line if Heapsort has to be tested
		// testHeapSort();

		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		Graph g = Graph.readGraph(in, false);
		// long avgTime; //Manually taken the avg of 5 runs

		//Uncomment below line to test MST 1
		testMST1(g);
		
		Helper.resetGraph(g);
		//Uncomment below line to test MST 2
		testMST2(g);
	}

	/**
	 * Executes the implementation of MST1 (Prims algorithm using priority queue) on input graph and captures the time and memory.
	 * @param g : Graph : input graph
	 */
	static void testMST1(Graph g){
		Timer timer = new Timer();
		int wmst = MST.PrimMST1(g);
		timer.end();
		System.out.println(timer);
		System.out.println("MST Weight "+wmst);
	}
	
	/**
	 * Executes the implementation of MST2 (Prims algorithm using indexed priority queue) on input graph and captures the time and memory. 
	 * @param g
	 */
	static void testMST2(Graph g){
		Timer timer = new Timer();
		int wmst = MST.PrimMST2(g);
		timer.end();
		System.out.println(timer);
		System.out.println("MST Weight "+wmst);
	}
	
	/**
	 * Method to test the HeapSort implementation of BinaryHeap
	 */
	static void testHeapSort() {
		int n = 20;
		Integer[] inputArr = new Integer[n + 1];
		int i;
		for (i = 0; i <= n; i++)
			inputArr[i] = new Integer(i);
		Shuffle.shuffle(inputArr, 1, n);
		Shuffle.printArray(inputArr, 1, n, "After Shuffle");
		BinaryHeap<Integer> binaryHeap = new BinaryHeap<Integer>(inputArr,
				new IntegerComparator());
		System.out.print("Heap Printing ");
		BinaryHeap.heapSort(inputArr, new IntegerComparator());
		for (i = 1; i <= n; i++)
			System.out.print(inputArr[i] + " ");
		binaryHeap.insert(new Integer(0));
		System.out.println("\nAfter Insert head " + binaryHeap.peek());
	}
}
