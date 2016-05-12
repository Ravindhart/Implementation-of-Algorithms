/**
 * Class to shuffle the input data
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/11/2016 9:52 pm
 */

import java.util.Random;

/* Shuffle the elements of an array A[from..to] randomly */
public class Shuffle {
	public static void shuffle(int[] A, int from, int to) {
		int n = to - from + 1;
		Random rand = new Random();
		for (int i = 1; i < n; i++) {
			int j = rand.nextInt(i);
			swap(A, i + from, j + from);
		}
	}

	static <T> void swap(int[] A, int x, int y) {
		int tmp = A[x];
		A[x] = A[y];
		A[y] = tmp;
	}

	static <T> void printArray(int[] arr, int from, int to, String message) {
		System.out.print(message);
		for (int i = from; i <= to; i++) {
			System.out.print(" " + arr[i]);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int n = 10;
		int[] arr = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			arr[i] = i;
		}
		printArray(arr, 1, n, "Before:");
		shuffle(arr, 1, n);
		printArray(arr, 1, n, "After:");
	}
}

/**
 * Sample output: Before: 1 2 3 4 5 6 7 8 9 10 After: 3 7 9 8 2 4 6 1 10 5
 */
