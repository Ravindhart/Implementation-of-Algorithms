import java.util.Comparator;

/**
 * simple integer comparator class
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
 *
 */
public class IntegerComparator implements Comparator<Integer> {

	@Override
	/**
	 * Compares its two arguments for order.  Returns a negative integer,
	 * zero, or a positive integer as the first argument is less than, equal
	 * to, or greater than the second. ~ java doc
	 */
	public int compare(Integer o1, Integer o2) {
		return o1.compareTo(o2);
	}

}
