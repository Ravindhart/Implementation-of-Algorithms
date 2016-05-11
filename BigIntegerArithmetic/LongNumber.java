/**
 * Class Defining the Big Integer
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/11/2016 2:40 pm
 */
import java.util.ArrayList;
import java.util.List;


public class LongNumber {

	List<Long> convertedNumber; // represents the number converted to base
	List<Long> decimalList; // represents the number in decimal
	long base = 10; // represents the base in which the number is stored
	int baseLength; // used for capturing the length of base
	boolean isNegative = false;
	List<Long> tensPowerList; // list represents 10^baseLength value converted
								// to base
	List<Long> baseInDecimalList; // list represents base value converted to
									// decimal - This does not require Long, can
									// be done in Integer but as product is
									// defined for Long lists, it is also
									// defined as Long

	/**
	 * Constructs LongNumber with the specified value in 'base'
	 * 
	 * @param inputNumber
	 *            : Long : input long value
	 */
	public LongNumber(Long inputNumber) {
		updateBaseLenth(base);
		if(inputNumber < 0)
			isNegative = true;
		convertedNumber = convertToBase(Math.abs(inputNumber), base);
		decimalList = populateDecimalList(inputNumber);
	}

	/**
	 * Constructs LongNumber with the specified String value converted to number
	 * in preset 'base'
	 * 
	 * @param inputStr
	 *            : String : input number in String format
	 */
	public LongNumber(String inputStr) {
		updateBaseLenth(base);
		findTensPowerList();
		isNegativeStr(inputStr);
		if(isNegative)
			inputStr = inputStr.substring(1);
		inputStr = removeLeadingZeros(inputStr);
		convertedNumber = convertToAnyBase(inputStr, base);
		decimalList = populateDecimalList(inputStr);
	}

	/**
	 * Constructs LongNumber with specified List and base
	 * @param number : List<Long> : input list representing number in base 'base'
	 * @param base : long : base
	 */
	public LongNumber(List<Long> number, long base, boolean isNegative){
		this.convertedNumber = number;
		this.base = base;
		this.isNegative = isNegative;
	}
	
	/**
	 * Checks if the input string represents negative number
	 * @param str : String : input String
	 */
	void isNegativeStr(String str){
		char i = str.charAt(0);
		if(i == '-')
			isNegative = true;
	}
	/**
	 * Populates tensPowerList which is 10^baseLength number in 'base'.
	 */
	void findTensPowerList() {
		int i = 0;
		long basePower = 1;

		// Invariant : Loop generates the 10 to the power of baseLength value
		// which is further sent convertToBase to convert to base 'base'
		while (i < baseLength) {
			basePower *= 10;
			i++;
		}
		tensPowerList = convertToBase(basePower, base);
	}

	void updateBaseLenth(long b) {
		baseLength = String.valueOf(b).length();
	}

	void updateDecimalsList() {
		baseInDecimalList = convertToBase(base, 10);
	}

	/**
	 * Returns a list of long values which represents the number (str) in base
	 * 'toBase'.
	 * 
	 * @param str
	 *            : String : input number in String format
	 * @param toBase
	 *            : long : base value to which the number has to be converted
	 * @return : List<Long> : List representing the string number in toBase
	 *         base.
	 */
	List<Long> convertToAnyBase(String str, long toBase) {

		// if input string is null, we do not need to prepare any List
		if (str == null || "".equals(str))
			return null;

		int index = 0;
		List<Long> number = new ArrayList<Long>();
		int len = str.length();

		// if input string is less than base itself
		if (len < baseLength) {
			number.add(new Long(str));
			return number;
		}

		// if string length and base lenth are 1 each (will not process for
		// loop)
		if (len == 1 && baseLength == 1) {
			number.addAll(convertToBase(Long.valueOf(str), toBase));
			return number;
		}
		int i;
		long digit;
		List<Long> prevList = null;
		List<Long> digitList = null;

		// Invariant : digit represents number of size 'baseLength' from LSB ->
		// MSB, (eg if baseLength is 2 and str is 165, initially digit will be
		// '65'), digitList is the list formed by converting digit to toBase,
		// number represents entire String converted to toBase base, prevList
		// represents the 10^ i-1 in ith iteration which is multiplied with
		// tensPowerList and later with digit at i.
		// Values Example : str = "165", base = 16 -> baseLength = 2,
		// tensPowerList (100)
		// -> 4-6, 1st iteration: digit - 65, digitList -> 1-4, in the next
		// Iteration, we take 1, convert 1 to toBase and take 100 convert to
		// toBase, multiple 1 with 100 in converted bases and add to 65 in
		// converted base
		for (i = len - 1; i > 0; i = i - baseLength) {
			digit = Long.valueOf(str.substring(len - baseLength - index, len
					- index));
			digitList = convertToBase(digit, toBase);
			if (index > 0) {
				prevList = updatePrevList(prevList, index, toBase);
				digitList = Product.product(digitList, prevList, toBase);
				ArithmeticOperations.addToX(number, digitList, toBase);
			} else {
				number.addAll(digitList); // check this if we can do this
			}
			index = index + baseLength;
		}

		// if the length of the string is not a multiple of baseLength, we might
		// miss few characters, below loop will handle that case
		if (len % baseLength != 0) {
			digit = Long.valueOf(str.substring(0, len - index));
			digitList = convertToBase(digit, toBase);
			prevList = updatePrevList(prevList, index, toBase);
			digitList = Product.product(digitList, prevList, toBase);
			ArithmeticOperations.addToX(number, digitList, toBase);
		}
		return number;
	}

	/**
	 * Returns list after multiplying prevList to index power of decimal. eg.
	 * index = 6 and baseLength of 2 represents prevList will be 10^4 and below
	 * method updates prevList to 10^6 by multiplying 10^4 with 10^2
	 * 
	 * @param prevList
	 *            : List<Long> : List of long represents the 10^index-baseLength
	 *            converted to toBase
	 * @param index
	 *            : int : index position
	 * @param toBase
	 *            : long : base in which prevList is represented and further
	 *            representations done
	 * @return : List<Long> : list of long numbers representing 10^index in
	 *         toBase
	 */
	List<Long> updatePrevList(List<Long> prevList, int index, long toBase) {
		// if index is same baseLength implies its the one's position
		if (index == baseLength) {
			prevList = new ArrayList<Long>();
			prevList.addAll(tensPowerList);
			return prevList;
		}
		// if not in one's position, we just need to multiply tensPowerList with
		// prevList
		prevList = Product.product(prevList, tensPowerList, toBase);
		return prevList;
	}

	/**
	 * Returns a list of long integers representing digit in toBase.
	 * 
	 * @param digit
	 *            : long : number to be converted to toBase
	 * @param toBase
	 *            : long : base
	 * @return : List<Long> : list of long integers representing digit after
	 *         base conversion
	 */
	List<Long> convertToBase(long digit, long toBase) {
		List<Long> childList = new ArrayList<Long>();
		long num = digit;

		//this is to handle the number 0
		if(digit == 0){
			childList.add(new Long(0));
			return childList;
		}
		// Invariant : num holds the digit and in each iteration it is updated
		// after part of it is converted to toBase. childList is used to hold
		// the converted numbers
		while (num > 0) {
			childList.add(num % toBase);
			num = num / toBase;
		}
		return childList;
	}

	/**
	 * Returns list of numbers representing the decimal version of String s.
	 * 
	 * @param s
	 *            : String : input number in string format
	 * @return : List<Integer> : list of number in decimal
	 */
	List<Long> populateDecimalList(String s) {
		List<Long> tempList = new ArrayList<Long>();
		int len = s.length();
		int i;
		for (i = len - 1; i >= 0; i--) {
			Character.getNumericValue(s.charAt(i));
			tempList.add(new Long(Character.getNumericValue(s.charAt(i))));
		}
		return tempList;
	}

	/**
	 * Returns list of numbers representing the decimal version of long num.
	 * 
	 * @param num
	 *            : long : input number in long format
	 * @return : List<Integer> : list of number in decimal
	 */
	List<Long> populateDecimalList(long num) {
		List<Long> tempList = new ArrayList<Long>();
		int modVal;
		if(num == 0){
			tempList.add(new Long(0));
			return tempList;
		}
		while (num != 0) {
			modVal = (int) num % 10;
			tempList.add(new Long(modVal));
			num /= 10;
		}
		return tempList;
	}

	/**
	 * Returns a list representing number in decimal format. uses the convertedNumber to convert to decimal.
	 * @return : List<Long> : long number representing number represented by this object in decimal
	 */
	List<Long> deriveDecimalList() {
		if (convertedNumber == null || convertedNumber.size() == 0)
			return null;

		int len = convertedNumber.size();
		List<Long> dList = new ArrayList<Long>();
		List<Long> prevBasePower = null;;
		int i;
        
		//Invariant: val represents each node value from convertedNumber, and this number alone is converted to base 10 and multiplied base^i to get actual value which is then added to previous list (represents digits already converted in previous iterations)
		for (i = 0; i < len; i++) {
			long val = convertedNumber.get(i);
			List<Long> tempList = populateDecimalList(val);
			if (i > 0) {
				prevBasePower = updateBasePowerList(prevBasePower, i);
				tempList = Product.product(tempList, prevBasePower, 10);
				ArithmeticOperations.addToX(dList, tempList, 10);
			} else 
				dList.addAll(tempList);
		}
		return dList;
	}

	/**
	 * Returns a list after updating the basePowerList by multiplying to the base power.
	 * @param baseList : List<Long> : list of long numbers which are to be multipled to power 'power'
	 * @param power : int : represents the power
	 * @return : List<Long> : represents a number which is equivalent to base^power in decimals
	 */
	List<Long> updateBasePowerList(List<Long> baseList, int power) {
		if(baseInDecimalList == null)
			updateDecimalsList();
		
		if (power == 1) {
			baseList = new ArrayList<Long>();
			baseList.addAll(baseInDecimalList);
			return baseList;
		}
		baseList = Product.product(baseList, baseInDecimalList, 10);
		return baseList;
	}

	/**
	 * Returns a list after converting the input digit to base 'toBase'
	 * @param digit : long : input digit which has to be converted to base 'toBase'
	 * @param toBase : int : base to which the number has to be converted
	 * @return : List<Long> : returns a number converted to 'toBase'
	 */
	List<Long> convertToTensBase(long digit, int toBase) {
		List<Long> childList = new ArrayList<Long>();
		long num = digit;
		while (num > 0) {
			childList.add(new Long((int) (num % toBase)));
			num = num / toBase;
		}
		return childList;
	}

	String removeLeadingZeros(String str){
		int i;
		char x;
		int len = str.length();
		for(i = 0; i< len; i++){
			x = str.charAt(i);
			if(x != '0')
				break;
		}
		return str.substring(i);
	}
	@Override
	public String toString() {
		if (decimalList == null)
			decimalList = deriveDecimalList();
		int len = decimalList.size();
		boolean forZeros = false;
		int i;
		StringBuilder response = new StringBuilder();
		if(len == 1){
			if(decimalList.get(0) == 0)
				return "0";
		}
		if(isNegative)
			response.append("-");
		for (i = len - 1; i >= 0; i--) {
			long decimal = decimalList.get(i);
			if (!forZeros && decimal == 0)
				continue;
			forZeros = true;
			response.append(decimal);
		}

		return response.toString();
	}

	/**
	 * Prints the number represented as a list
	 */
	void printList() {
		System.out.print(base + " : ");
		if(isNegative)
			System.out.print("-");
		if (convertedNumber != null)
			for (Long l : convertedNumber)
				System.out.print(l + " ");
		else
			System.out.print("list is empty");
		System.out.println();
	}

	static void testProduct() {
		ArrayList<Long> x1 = new ArrayList<Long>();
		x1.add(new Long(5));
		x1.add(new Long(2));
		List<Long> x12 = Product.product(x1, x1, 10);
		System.out.println("Results");
		for (Long x : x12)
			System.out.print(x + " ");
	}

	static void testLongConstructor() {
		Long l = new Long(165);
		LongNumber ln = new LongNumber(l);
		ln.printList();
	}

	static void testStringConstructor() {
		String l = new String("-000001256874");
		//String l = new String("1234567890");
		LongNumber ln = new LongNumber(l);
		ln.printList();
		System.out.println(ln);
	}
	
	public static void main(String[] args) {
		testStringConstructor();
		testLongConstructor();
	}

}
