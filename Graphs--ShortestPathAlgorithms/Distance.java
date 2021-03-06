/**
 * class for representing the distance of a vertex.
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/13/2016 10:53 pm
 */
 */
public class Distance {

	boolean isInfinity; //if set to true, represents distance as infinity
	int value;
	
	public Distance(boolean infinity, int val){
		isInfinity = infinity;
		value = val;
	}
	
	public Distance(){
		isInfinity = true;
		value = 0;
	}
	
	public void setInfinity(){
		isInfinity = true;
		value = 0;
	}
	
	public void setValue(int val){
		isInfinity = false;
		value = val;
	}
	
	public Distance(Distance another){
		isInfinity = another.isInfinity;
		value = another.value;
	}
	
	public void fill(Distance dis){
		isInfinity = dis.isInfinity;
		value = dis.value;
	}
}
