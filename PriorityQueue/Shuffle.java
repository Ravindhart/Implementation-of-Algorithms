
import java.util.Random;

/* Shuffle the elements of an array A[from..to] randomly 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 11:35 pm
 */
public class Shuffle {
    public static<T> void shuffle(T[] A, int from, int to) {
	int n = to - from  + 1;
	Random rand = new Random();
	for(int i=1; i<n; i++) {
	    int j = rand.nextInt(i);
	    swap(A, i+from, j+from);
	}
    }

    static<T> void swap(T[] A, int x, int y) {
	T tmp = A[x];
	A[x] = A[y];
	A[y] = tmp;
    }

    static<T> void printArray(T[] arr, int from, int to, String message) {
	System.out.print(message);
	for(int i=from; i<=to; i++) {
	    System.out.print(" " + arr[i]);
	}
	System.out.println();
    }

    public static void main(String[] args) {
	int n = 10;
        Integer[] arr = new Integer[n+1];
        for(int i=1; i<=n; i++) {
	    arr[i] = new Integer(i);
	}
	printArray(arr, 1, n, "Before:");
	shuffle(arr, 1, n);
	printArray(arr, 1, n, "After:");
    }
}

/** Sample output:
Before: 1 2 3 4 5 6 7 8 9 10
After: 3 7 9 8 2 4 6 1 10 5
*/
