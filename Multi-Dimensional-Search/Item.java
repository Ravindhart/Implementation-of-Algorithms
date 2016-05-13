import java.util.HashSet;
import java.util.Set;

/**
 * class defining the Item
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/12/2016 7:49 am
 */
public class Item {
	long id;
	Set<Long> description;
	double price;

	Item(double price){
		this.price = price;
		
	}
	Item(long itemId, long[] desc, double itemPrice, int size) {
		id = itemId;
		price = itemPrice;
		description = new HashSet<Long>();
		fillDescription(desc, size);
	}

	public void setId(long itemId) {
		id = itemId;
	}

	public long getId() {
		return id;
	}

	public void setPrice(double itemPrice) {
		price = itemPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setDescription(long[] desc, int size) {
		description = new HashSet<Long>();
		fillDescription(desc, size);
	}

	public Set<Long> getDescription() {
		return description;
	}

	void fillDescription(long[] descArr, int size){
		int i;
		for (i = 0; i < size; i++)
			description.add(descArr[i]);
	}
	
/*	@Override
	public int hashCode() {
		return Long.valueOf(id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Item another = (Item) obj;
		return this.id == another.id;
	}*/
}
