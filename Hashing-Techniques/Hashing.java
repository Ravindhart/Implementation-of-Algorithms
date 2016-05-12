import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

/**
 * Implements hashing techniques to solve basic problems like finding distinct
 * element in array, comparing hashing performance against Arrays.sort.
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/11/2016 9:51 pm
 *
 */
public class Hashing {

	/**
	 * Returns an integer which represents the index before which all the
	 * elements are distinct i.e. 0 - k-1 are distinct elements
	 * 
	 * @param arr
	 *            : T[] : array of objects, each object overrides hashCode and
	 *            equals method
	 * @return : int : returns index before which are distinct elements
	 */
	public static <T> int findDistinct(T[] arr) {
		HashSet<T> distinctSet = new HashSet<T>();
		int dupIndex = 0, i;
		int len = arr.length;

		// Invariant: Iterates through the array, in each iteration cur holds
		// the element of that iteration. for each element, we check if the
		// element is already present in our distinctSet(hashset of distinct
		// elements), if its present, means this is a duplicate and we can
		// ignore this element, if its not, we can swap this element with
		// duplicate. The below assumes that there will be atleast one duplicate
		// and so does swaps when it doesn't find a duplicate. Assuming there
		// are no duplciates, the loop ends up swapping elements with it self.
		// We can control this by having a boolean flag (implementation is
		// provided as version 2 below)
		for (i = 0; i < len; i++) {
			T cur = arr[i];
			if (!distinctSet.contains(cur)) {
				swap(arr, i, dupIndex);
				dupIndex++;
				distinctSet.add(cur);
			}
		}
		return dupIndex;
	}

	/**
	 * Swaps elements in array of specified locations
	 * 
	 * @param arr
	 *            : T[] : Array of objects
	 * @param from
	 *            : int : index which has to be swapped
	 * @param to
	 *            : int : another index
	 */
	public static <T> void swap(T[] arr, int from, int to) {
		T temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;

	}

	/**
	 * Returns the most frequent element in the array. This method leverages
	 * Arrays.sort method to initially sort the array and compare adjacent
	 * elements to find the most frequent element.
	 * 
	 * @param arr
	 *            : int[] : input int array
	 * @return : int : element which is most frequent in the array.
	 */
	public static int mostFrequent1(int[] arr) {
		int len = arr.length;
		if (len == 1)
			return arr[0];
		Arrays.sort(arr);
		int maxFreq = 0, i, maxFreqElement = arr[0];
		int freq = 1;

		// Invariant: freq counts the frequency of current element in the array,
		// it is incremented after checking if the adjacent elements are same,
		// if not it is reset to 1. Later it is compared to maxFreq which holds
		// the frequency of so far most frequent element. if the current
		// element's frequency is more than the mostFreq, we update the values
		// accordingly.
		for (i = 0; i < len - 1; i++) {
			if (arr[i] == arr[i + 1])
				freq++;
			else
				freq = 1;

			if (freq > maxFreq) {
				maxFreq = freq;
				maxFreqElement = arr[i];
			}
		}
		return maxFreqElement;
	}

	public static int mostFrequent2(int[] arr) {
		int len = arr.length;
		if (len == 1)
			return arr[0];
		HashMap<Integer, Integer> elementFreq = new HashMap<Integer, Integer>();
		int i, count;
		// Invariant : We iterate through all elements of the array and update
		// the elementFreq map which contains map of integers and their
		// frequency, if the element is already present in the map, we retrieve
		// its count and increment it, if not we add an entry for the element
		// with count as 1.
		for (i = 0; i < len; i++) {
			count = 0;
			if (elementFreq.containsKey(arr[i]))
				count = elementFreq.get(arr[i]);
			elementFreq.put(arr[i], count + 1);
		}
		int mostFreq = 0;
		int mostFreqElement = arr[0];

		// Invariant: Iterates through each entry of map which has element and
		// its frequency checking if the current element is most frequent;
		// updating the mostFreqElement as and when we find an element with so
		// far most frequent element. At the end of the loop, mostFreqElement
		// will have the most frequent element
		for (Map.Entry<Integer, Integer> entry : elementFreq.entrySet()) {
			int freq = entry.getValue();
			if (freq > mostFreq) {
				mostFreqElement = entry.getKey();
				mostFreq = freq;
			}
		}

		return mostFreqElement;
	}

	/**
	 * Sample main function to test the functions in this class.
	 * 
	 * @param args
	 *            : String[] : optional arguments
	 */
	public static void main(String[] args) {
		testMostFreq();
		Integer[] arr = new Integer[20];
		for (int i = 0; i < 20; i=i+2) {
			arr[i] = new Integer(i);
			arr[i+1] = new Integer(i);
		}
		
		for(Integer x : arr)
			System.out.print(x+" ");
		System.out.println(findDistinct(arr));
		for(Integer x : arr)
			System.out.print(x+" ");
	}
	
	public static void testMostFreq(){
		int[] arr = new int[20];
		Random rand = new Random();
		for(int i = 0; i<20; i++){
			arr[i] = rand.nextInt(11);
		}
		for(Integer x : arr)
			System.out.print(x+" ");
		System.out.println(mostFrequent1(arr));
	}

	/**
	 * Returns an integer which represents the index before which all the
	 * elements are distinct i.e. 0 - k-1 are distinct elements
	 * 
	 * @param arr
	 *            : T[] : array of objects, each object overrides hashCode and
	 *            equals method
	 * @return : int : returns index before which are distinct elements
	 */
	public static <T> int findDistinctV2(T[] arr) {
		HashSet<T> distinctSet = new HashSet<T>();
		int dupIndex = 0, i;
		int len = arr.length;
		boolean isDupFound = false;

		// Invariant: Iterates through the array, in each iteration cur holds
		// the element of that iteration. for each element, we check if the
		// element is already present in our distinctSet(hashset of distinct
		// elements), if its present, means this is a duplicate and if this is
		// the first duplicate, we set the flag isDupFound to true indicating we
		// have found atleast one duplciate. If its not present in the
		// distinctSet, we check if we have seen any duplciates so far, if so we
		// swap this element with duplicate and add this element to distinct
		// set, if not we just add this element to distinct set and proceed with
		// loop.
		for (i = 0; i < len; i++) {
			T cur = arr[i];
			if (distinctSet.contains(cur))
				isDupFound = true;
			else {
				if (isDupFound)
					swap(arr, i, dupIndex);
				dupIndex++;
				distinctSet.add(cur);
			}
		}
		return dupIndex;
	}
}
/*
 * Usage : Hashing.findDistinct(arr)
 * Note: arr should hold objects which implement hashCode and equals methods
 * sampel i/p array - 0 0 2 2 4 4 6 6 8 8 10 10 12 12 14 14 16 16 18 18
 * output  - 10.
 * array looks like - 0 2 4 6 8 10 12 14 16 18 4 10 2 12 6 14 0 16 8 18 
 * 
 * Usage : Hashing.mostFrequent1(arr)
 * sample i/p - 8 0 7 6 8 1 9 1 6 2 10 3 8 4 7 9 7 1 0 1 
 * o/p - 1
 * 
 */
