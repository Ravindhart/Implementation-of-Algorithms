/**
 * Implementation of this interface will let user to update and retrieve index.
 * 
 * @author G02 (Bala Chandra Yadav, Ravindhar, Mohammad Rafi)
 * 
 * @param: T: any object
 */
public interface Index<T> {
	
	/**
	 * Updates the index to t
	 * @param t : Entry<T> : input Entry<T> to which the index will be updated
	 */
	public void putIndex(Entry<T> t);

	/**
	 * Returns Entry<T> which represents the index
	 * @return : Entry<T> : returns index
	 */
	public Entry<T> getIndex();

}
