import java.util.Random;

/**
 * Sample Driver program for testing the performance of mostFrequent method
 * implementations i.e. Hashing Vs Sorting
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/11/2016 9:50 pm
 *
 */
public class Driver {
	/**
	 * Sample main function which tests the performance of mostFrequent methods
	 * of Hashing
	 * 
	 * @param args
	 *            : String[] : optional arguments
	 */
	public static void main(String[] args) {
		int n;
		// if user hasn't provided n, it defaults it to 1 million
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		} else {
			n = 1000000;
		}
		int i;
		int[] arr = new int[n];
		for (i = 1; i <= n; i++) {
			arr[i-1] = n%i;
		}

		// Shuffle.shuffle(arr, 0, n-1);
		Timer timer = new Timer();
		timer.start();
		int mostFreq = Hashing.mostFrequent1(arr);
		//Comment above line and uncomment below to test Hashing.mostFrequent2
		//int mostFreq = Hashing.mostFrequent2(arr);
		timer.end();
		System.out.println(timer);
		System.out.println(mostFreq);
		
		//Uncomment below line to test Hashing.findDistinct(arr)
		//testFindDistinct(n);
	}

	/**
	 * Test the method findDistinct from Hashing
	 * 
	 * @param n
	 *            : int : input array size
	 */
	public static void testFindDistinct(int n) {
		int i;
		Integer[] arr = new Integer[n];
		Random rand = new Random();
		for (i = 0; i < n; i++) {
			arr[i] = new Integer(rand.nextInt(n / 2 + 1));
		}

		Timer timer = new Timer();
		timer.start();
		int indexK = Hashing.findDistinct(arr);
		timer.end();
		System.out.println("index k" + indexK);
	}
}
