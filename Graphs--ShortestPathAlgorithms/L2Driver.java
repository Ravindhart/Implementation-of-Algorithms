/**
 * simple driver program for testing Shortest Path implementations
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 10:53 pm
 */
 
 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class L2Driver {
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
		Graph g = Graph.readGraph(in, true);
		ShortestPaths.findAllShortestPaths(g);
	}
}
