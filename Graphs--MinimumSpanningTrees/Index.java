/**
 * Implementation of this interface will let user to update and retrieve index.
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/15/2016 12:21 pm
 *
 */
public interface Index {
	/**
	 * updates the index with the given index
	 * 
	 * @param index
	 *            : int : index
	 */
	public void putIndex(int index);

	/**
	 * Returns the index
	 * 
	 * @return : int: index
	 */
	public int getIndex();
}
