
/**
 * Implementation of this interface will let user to update and retrieve index.
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
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
