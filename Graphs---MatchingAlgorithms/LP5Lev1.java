import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * simple driver program for testing bipartite matching Level1  
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
 *
 */
public class LP5Lev1 {
	/**
	 * main method which reads input data from either System.in or file and
	 * prints the results of the test.
	 * 
	 * @param args
	 *            : input test file location if any
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Scanner in;
		boolean VERBOSE=false;
		if (args.length == 0) {
			in = new Scanner(System.in);
			
		} else {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
			if(args.length==2)
			{
				VERBOSE=true;
			}
		}
		Graph g = Graph.readGraph(in, true);
		Timer t=new Timer();
		t.start();
		int card = Matching.maxMatchingBGraph(g);
		t.end();
		if(card==-1)
		{
			System.out.println("G is not bipartite");
		}
		else
		{	
		System.out.println(card);
		if(VERBOSE)
			Helper.printMatching(g);
		}
		System.out.println(t.toString());
	}
}