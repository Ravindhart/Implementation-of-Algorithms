
/**
 * class implementing the multi dimensional search operations
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/12/2016 7:47 pm
 *
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

//import java.util.TreeSet;

public class MDS {

	final double epsilon = 0.000001;
	TreeMap<Long, Item> idItemMap = new TreeMap<Long, Item>();
	TreeMap<Long, Set<Item>> descItemMap = new TreeMap<Long, Set<Item>>();
	TreeMap<Double, Set<Item>> priceItemMap = new TreeMap<Double, Set<Item>>();
	TreeMap<Integer, TreeMap<Long, ArrayList<Item>>> sameItems = new TreeMap<Integer, TreeMap<Long, ArrayList<Item>>>();

	/**
	 * Returns 0 if there exists an item with the details provided after
	 * updating the existing item with new values, if there is no item matching
	 * the id, it creates a new item inserts the same in to relevant data
	 * structures and returns 1.
	 * 
	 * @param id
	 *            : long : id of the new item
	 * @param price
	 *            : double : price of the new item
	 * @param description
	 *            : long[] : description of the item
	 * @param size
	 *            : int : represents the index, till which the description array
	 *            holds items description
	 * @return : int : 0 if the item is already present, 1 if the item is new
	 */
	int insert(long id, double price, long[] description, int size) {

		// If the item is new
		if (!idItemMap.containsKey(id)) {
			Item newItem = new Item(id, description, price, size);
			idItemMap.put(id, newItem);
			insertIntoDescMap(newItem);
			insertIntoPriceMap(newItem);
			insertIntoSameMap(newItem);
			return 1;
		} else { // If the item already exists
			Item existedItem = idItemMap.get(id);

			if (existedItem.price != price) {
				removeFromPriceMap(existedItem);
				existedItem.price = price;
				insertIntoPriceMap(existedItem);
			}

			if (size > 0) {
				removeFromDescMap(existedItem);
				removeFromSameMap(existedItem);
				/*
				 * List<Long> sList = (List) Arrays.asList(description);
				 * updateDescMap(existedItem, new HashSet<Long>(sList));
				 */
				existedItem.setDescription(description, size);
				insertIntoDescMap(existedItem);
				insertIntoSameMap(existedItem);
			}
		}
		return 0;
	}

	/**
	 * Places an item into priceMap with its price being the key
	 * 
	 * @param item
	 *            :Item: the item to be placed into the map
	 */
	public void insertIntoPriceMap(Item item) {
		double price = item.price;
		if (priceItemMap.containsKey(price))
			priceItemMap.get(price).add(item);
		else {
			Set<Item> newSet = new HashSet<Item>();
			newSet.add(item);
			priceItemMap.put(price, newSet);
		}
	}

	/**
	 * Removes an item from the priceMap
	 * 
	 * @param item
	 *            : Item: the item to be removed from the map
	 */
	public void removeFromPriceMap(Item item) {
		double price = item.price;
		if (priceItemMap.containsKey(price)) {
			Set<Item> itemSet = priceItemMap.get(price);
			if (itemSet.size() == 1)
				priceItemMap.remove(price);
			else
				itemSet.remove(item);
		}
	}

	/**
	 * Places an item into descriptionMap with its each description value being
	 * the key.
	 * 
	 * @param item
	 *            :Item: the item to be placed into the map
	 */
	public void insertIntoDescMap(Item item) {

		Set<Long> description = item.description;
		//For each description value, descItemMap is updated
		for (Long descL : description) {
			insertDescIntoMap(descL, item);
		}
	}

	/**
	 * Removes an item from the descriptionMap.
	 * 
	 * @param item
	 *            : Item: the item to be removed from the map
	 * @param description
	 *            : long[]: description array
	 * @param size
	 *            : int: size of the description array
	 */
	public void removeFromDescMap(Item item) {

		Set<Long> description = item.description;
		//For each description value, descItemMap is updated
		for (Long descL : description) {
			if (descItemMap.get(descL).size() == 1)
				descItemMap.remove(descL);
			else
				descItemMap.get(descL).remove(item);
		}
	}

	/**
	 * Updates Description Map when an item gets updated
	 * 
	 * @param item
	 *            : Item: the item which got updated
	 * @param updatedDesc
	 *            : Set<Long>: the updated Description of the item
	 */
	public void updateDescMap(Item item, Set<Long> updatedDesc) {
		Set<Long> prevDesc = item.description;
		//For each desc value, we appropriately update description map after checking it existence 
		for (long desc : updatedDesc) {
			if (prevDesc.contains(desc))
				continue;
			insertDescIntoMap(desc, item);
		}
		//For each desc value, we appropriately update description map after checking it existence
		for (long desc : prevDesc) {
			if (updatedDesc.contains(desc))
				continue;
			removeDescFromMap(desc, item);
		}
	}

	/**
	 * Inserts an item into descItemMap with desc value being the key
	 * 
	 * @param descriptionValue
	 *            : long: description Value
	 * @param item
	 *            :Item: Item to be inserted
	 */
	public void insertDescIntoMap(long descriptionValue, Item item) {
		if (descItemMap.containsKey(descriptionValue))
			descItemMap.get(descriptionValue).add(item);
		else {
			Set<Item> newSet = new HashSet<Item>();
			newSet.add(item);
			descItemMap.put(descriptionValue, newSet);
		}
	}

	/**
	 * Removes an item from descItemMap with desc value being the key
	 * 
	 * @param descriptionValue
	 *            :long: description Value
	 * @param item
	 *            ; Item: Item to be removed
	 */
	public void removeDescFromMap(long descriptionValue, Item item) {
		if (descItemMap.get(descriptionValue).size() == 1)
			descItemMap.remove(descriptionValue);
		else
			descItemMap.get(descriptionValue).remove(item);
	}

	/**
	 * Inserts an item into a sameItem map
	 * 
	 * @param item
	 *            :Item: the given item
	 */
	public void insertIntoSameMap(Item item) {
		int size = item.description.size();

		if (size < 8)
			return;

		long descSum = getDescSum(item);

		if (sameItems.containsKey(size)) {
			TreeMap<Long, ArrayList<Item>> innerMap = sameItems.get(size);

			if (innerMap.containsKey(descSum))
				innerMap.get(descSum).add(item);
			else {
				ArrayList<Item> newList = new ArrayList<Item>();
				newList.add(item);
				innerMap.put(descSum, newList);
			}
		} else {
			ArrayList<Item> newList = new ArrayList<Item>();
			newList.add(item);
			TreeMap<Long, ArrayList<Item>> newMap = new TreeMap<Long, ArrayList<Item>>();
			newMap.put(descSum, newList);
			sameItems.put(size, newMap);
		}
	}

	/**
	 * removes an item from SameMap
	 * 
	 * @param item
	 *            : Item: Item to be removed from the map
	 */
	public void removeFromSameMap(Item item) {
		int size = item.description.size();
		if (size < 8)
			return;

		long descSum = getDescSum(item);
		TreeMap<Long, ArrayList<Item>> innerMap = sameItems.get(size);
		ArrayList<Item> itemList = innerMap.get(descSum);

		if (itemList.size() == 1) {
			if (innerMap.size() == 1)
				sameItems.remove(size);
			else
				innerMap.remove(descSum);
		} else
			itemList.remove(item);
	}

	/**
	 * Gets the sum of the description values of an item
	 * 
	 * @param item
	 *            : Item: Item for which sum has to be calculated
	 * @return: long: returns the sum of description values
	 */
	public long getDescSum(Item item) {
		long sum = 0;
		Set<Long> description = item.description;
		for (long desc : description)
			sum += desc;
		return sum;
	}

	/**
	 * finds the existence of an item with the given id
	 * 
	 * @param id
	 *            : long: id of the item
	 * @return: double : If item exists returns the price of the item else 0
	 */
	double find(long id) {
		if (idItemMap.containsKey(id))
			return idItemMap.get(id).price;
		return 0;
	}

	/**
	 * deletes an item from storage
	 * 
	 * @param id
	 *            : long: id of the item
	 * @return: long: if item exists returns the sum of values in the
	 *          description else returns 0
	 */
	long delete(long id) {

		if (idItemMap.containsKey(id)) {
			Item item = idItemMap.get(id);
			removeFromPriceMap(item);
			long descSum = getDescSum(item);
			removeFromDescMap(item);
			removeFromSameMap(item);
			idItemMap.remove(id);
			return descSum;
		}
		return 0;
	}

	/**
	 * find items whose description contains given n and return lowest price of
	 * those items.
	 * 
	 * @param des
	 *            : long: description
	 * @return: double: the lowest price of the matched items
	 */
	double findMinPrice(long des) {

		if (descItemMap.containsKey(des)) {
			Set<Item> itemSet = descItemMap.get(des);
			double minPrice = Double.MAX_VALUE;
			for (Item eachItem : itemSet) {
				if (minPrice > eachItem.price)
					minPrice = eachItem.price;
			}
			return minPrice;
		}
		return 0;
	}

	/**
	 * finds items whose description contains given n and return highest price
	 * of those items.
	 * 
	 * @param des
	 *            : long: description
	 * @return: double: the highest price of the matched items
	 */
	double findMaxPrice(long des) {

		if (descItemMap.containsKey(des)) {
			Set<Item> itemSet = descItemMap.get(des);
			double maxPrice = Double.MIN_VALUE;
			for (Item eachItem : itemSet) {
				if (maxPrice < eachItem.price)
					maxPrice = eachItem.price;

			}
			return maxPrice;
		}
		return 0;
	}

	/**
	 * finds the number of items whose description contains des and their prices
	 * fall within the given range [lowPrice, highPrice]
	 * 
	 * @param des
	 *            : double: description of the item
	 * @param lowPrice
	 *            : double: the lower bound of the price range given
	 * @param highPrice
	 *            : double: the upper bound of the price range given
	 * @return: int: the number of items which satisfy the given criteria
	 */
	int findPriceRange(long des, double lowPrice, double highPrice) {

		if (descItemMap.containsKey(des)) {
			Set<Item> itemSet = descItemMap.get(des);
			int itemCount = 0;
			for (Item eachItem : itemSet) {
				if (eachItem.price >= lowPrice && eachItem.price <= highPrice)
					itemCount++;
			}
			return itemCount;
		}
		return 0;
	}

	/**
	 * increases the price of every product, whose id is in the range
	 * [minid,maxid]
	 * 
	 * @param minid
	 *            : long: the lower bound of the range of item ids
	 * @param maxid
	 *            : long: the upper bound of the range of item ids
	 * @param rate
	 *            : double: the percentage of the increment to be made
	 * @return: double: returns the total hike
	 */
	double priceHike(long minid, long maxid, double rate) {
		double totalHike = 0;

		for (Long id : idItemMap.subMap(minid, true, maxid, true).keySet()) {
			Item existedItem = idItemMap.get(id);
			double price = existedItem.price;
			double hike = (price * rate) / 100.0;
			removeFromPriceMap(existedItem);
			double newPrice = ignorePennies(price + hike);
			totalHike = totalHike + newPrice - price;
			existedItem.setPrice(newPrice);
			insertIntoPriceMap(existedItem);
		}
		return ignorePennies(totalHike);
	}

	/**
	 * computes the number of items whose price is in the range [lowPrice,
	 * highPrice]
	 * 
	 * @param lowPrice
	 *            : double: the lower bound of the given price range
	 * @param highPrice
	 *            : double: the upper bound of the given price range
	 * @return: int: the number of items whose price falls in the given range
	 */
	int range(double lowPrice, double highPrice) {
		int itemCount = 0;
		for (Double price : priceItemMap.keySet()) {
			if (price < lowPrice)
				continue;
			if (price > highPrice)
				break;

			itemCount = itemCount + priceItemMap.get(price).size();
		}
		return itemCount;
	}

	/**
	 * Finds the number of items that satisfy the conditions that the
	 * description of the item contains 8 or more numbers and the description of
	 * the item contains exactly the same set of numbers as another item
	 * 
	 * @returns: int: number of items that satisfy the above constraints
	 */
	int samesame() {

		TreeMap<Long, ArrayList<Item>> innerMap;
		ArrayList<Item> itemList;
		int size;
		int sameCount = 0;
		Set<Integer> allSizes = sameItems.keySet();
		// Loop Invariant: iterates samItems map to get identify existence of an
		// item with similar description as that of the current item
		// iSize: iSize entry contains all the items with the same description
		// array size
		// sum: sum entry contains all the items with the same sum of
		// description array
		for (Integer iSize : allSizes) {
			innerMap = sameItems.get(iSize);
			Set<Long> descSums = innerMap.keySet();
			for (Long sum : descSums) {
				itemList = innerMap.get(sum);
				size = itemList.size();
				for (int i = 0; i < size; i++)
					for (int j = 0; j < size; j++) {
						if (i == j)
							continue;
						if (itemList.get(i).description.equals(itemList.get(j).description)) {
							sameCount++;
							break;
						}
					}
			}
		}
		return sameCount;
	}

	/**
	 * returns the rounded value of val
	 * 
	 * @param val:
	 *            double: input
	 * @return: double: rounded value
	 */
	double ignorePennies(double val) {
		return Math.floor((val + epsilon) * 100) / 100;
	}

}
