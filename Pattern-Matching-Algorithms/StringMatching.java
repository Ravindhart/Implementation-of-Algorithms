/**
 * Class implementing pattern matching algorithms
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/11/2016 10:14 pm
 */
public class StringMatching {

	public final static int base = 31;
	public final static int prime = 1000000007;
	public static int mulFactor = 1;

	/**
	 * Returns no of valid shifts possible on the text for the pattern.
	 * 
	 * @param text
	 *            : String : input String
	 * @param pattern
	 *            : String: pattern which is being searched
	 * @return : int : no of valid shifts
	 */
	public static int naiveStringMatcher(String text, String pattern) {
		int n = text.length();
		int m = pattern.length();
		int validShifts = 0;
		int i;

		// Loop Invariant: Compares the pattern in text by sliding each
		// character in each iteration
		for (i = 0; i <= n - m; i++) {
			if (pattern.equals(text.substring(i, i + m)))
				validShifts++;
		}
		return validShifts;
	}

	/**
	 * Returns no of valid shifts possible on the text for the pattern,
	 * implements Rabin Karp algorithm
	 * 
	 * @param text
	 *            : String : input text
	 * @param pattern
	 *            : String : input pattern
	 * @return : int : no of valid shifts possible for the pattern over text
	 */
	public static int rabinKarpMatcher(String text, String pattern) {
		int m = pattern.length();
		updateMulFactor(m);
		int n = text.length();
		int pHash = getHash1(pattern, m);
		int tHash = getHash1(text, m);
		int i;
		int validShifts = 0;

		// Loop Invariant : pHash holds the hash value for the pattern, tHash
		// holds the hash value for the evaluating portion of text string, in
		// each iteration we check if the hash values are same, if they are same
		// strings might match, to confirm the same we use naiveStringMatcher
		// for the respective portion of the text and pattern. If not, we
		// proceed to slide to right on text
		for (i = 0; i <= n - m; i++) {
			if (pHash == tHash)
				validShifts = validShifts
						+ naiveStringMatcher(text.substring(i, i + m), pattern);
			if (i == n - m)
				break;
			tHash = updateHash1(text, i, i + m, tHash);
		}
		return validShifts;
	}

	/**
	 * Returns no of valid shifts possible on the text for the pattern,
	 * implements KMP algorithm
	 * 
	 * @param text
	 *            : String : input text
	 * @param pattern
	 *            : String : input pattern
	 * @return : int : no of valid shifts possible for the pattern over text
	 */
	public static int kmpMatcher(String text, String pattern) {
		int m = pattern.length();
		int[] prefix = prefixFunc(pattern);
		int q = -1;
		int validShifts = 0;
		int n = text.length();

		// Loop Invariant : using the prefix table, we shift the slider to the
		// position (from prefix table) where the previous character matched.
		// The process is like it compares the characters from beginning, if
		// there is a mismatch, it falls back to the last matched character
		// prefix position.
		for (int i = 0; i < n; i++) {
			while (q >= 0 && pattern.charAt(q + 1) != text.charAt(i))
				q = prefix[q];
			if (pattern.charAt(q + 1) == text.charAt(i))
				q++;
			if (q == m - 1) {
				validShifts++;
				q = prefix[q];
			}
		}
		return validShifts;
	}

	/**
	 * Helper call which updates the mulFactor which is used to calculate the
	 * hash value.
	 * 
	 * @param m
	 *            : int : length of the pattern
	 */
	static void updateMulFactor(int m) {
		mulFactor = power(base, m - 1) % prime;
	}

	/**
	 * Returns the hash value for the given string, considers the first m
	 * characters from the input string. Uses the formula s1*(base^m-1) +
	 * s2*(base^m-2)+..+sm*1 for string s1s2s3s4...sm.
	 * 
	 * @param str
	 *            : String : input string for which the hash has to be
	 *            calculated
	 * @param m
	 * @return
	 */
	public static int getHash(String str, int m) {
		int h = 0;
		for (int i = 0; i < m; i++) {
			h = (base * h + str.charAt(i)) % prime;
		}
		return h;
	}
	public static int getHash1(String str, int m) {
		int h = 0;
		for (int i = 0; i < m; i++) {
			h = (h + str.charAt(i)) % prime;
		}
		return h;
	}

	/**
	 * Updates the pHash by ignoring the character at start and considering the character at end from text. 
	 * @param text : String : input text
	 * @param start : int : start position of the character which has to be ignored from previously calculated hash
	 * @param end : int : position of the character which has to be added to previously calculated hash
	 * @param pHash : int : calculated hash
	 * @return : int : returns updated hash
	 */
	static int updateHash(String text, int start, int end, int pHash) {
		pHash = (((pHash - text.charAt(start) * mulFactor) * base) + text
				.charAt(end)) % prime;
		return pHash;
	}
	
	static int updateHash1(String text, int start, int end, int pHash) {
		pHash = (pHash - text.charAt(start) + text.charAt(end)) % prime;
		return pHash;
	}
	/**
	 * Returns a raised to b.
	 * @param a : int : input integer
	 * @param b : int : input power
	 * @return : int : a raised to b, includes modulo prime to keep the value in int range
	 */
	static int power(int a, int b) {
		if (b == 0)
			return 1;
		if (b == 1)
			return a;
		int sqr = (a * a) % prime;
		int res = power(sqr, b / 2);
		if (b % 2 == 0)
			return res;
		else
			return a * res;
	}

	/**
	 * Returns integer array which represents the prefix. i.e. prefix[2] represents longest prefix in pattern[0-2] which is also a suffix of the pattern[0-2.
	 * @param pattern : String : input pattern
	 * @return : int[] : prefix array
	 */
	public static int[] prefixFunc(String pattern) {
		int len = pattern.length();
		int[] prefix = new int[len];
		int k = -1;
		prefix[0] = k;
		int q;
		for (q = 1; q < len; q++) {
			while (k >= 0 && pattern.charAt(k + 1) != pattern.charAt(q))
				k = prefix[k];
			if (pattern.charAt(k + 1) == pattern.charAt(q))
				k++;
			prefix[q] = k;
		}
		return prefix;
	}
}
