import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * simple driver program for testing EulerUtil
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
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
		Scanner in;
		if (args.length > 0) { 
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		Graph g = Graph.readGraph(in, false);
		Timer timer = new Timer();
		timer.start();
		DoublyLinkedList<Edge> dll = EulerUtil.findEulerTour(g);
		EulerUtil.verifyTour(dll); 
		timer.end();
		
		if (dll == null)
			System.out.println("Graph is not Eulerian");
		else
            dll.printList();
		System.out.println(timer);
	}
}
