/**
 * Class implementing Helper functions
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/11/2016 2:42 pm
 */
import java.util.Iterator;


public class Helper<T> {
	/**
	 * helper function that returns next object if available else null
	 * 
	 * @param iterator
	 *            : Iterator : Iterator of Integers which has to be iterated
	 * @return T if available or null
	 */
	public static <T> T next(Iterator<T> iterator) {
		if (iterator.hasNext())
			return iterator.next();
		else
			return null;
	}
}
