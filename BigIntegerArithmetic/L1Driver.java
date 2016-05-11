public class L1Driver {
	public static void main(String[] args) {
        LongNumber l1 = new LongNumber("124257896");
        LongNumber l2 = new LongNumber("13478");
        LongNumber l3 = new LongNumber("13");
        LongNumber addition = ArithmeticOperations.add(l1,l2);
        LongNumber difference = ArithmeticOperations.subtract(l1,l2);
        LongNumber product = ArithmeticOperations.product(l1,l2);
        LongNumber quotient = ArithmeticOperations.divide(l1,l2);
        LongNumber mod = ArithmeticOperations.mod(l1,l2);
        LongNumber pow = ArithmeticOperations.power(l1,l2);
        LongNumber squareroot=ArithmeticOperations.squareRoot(l1);
        addition.toString();
        difference.toString();
        product.toString();
        quotient.toString();
        mod.toString();
        pow.toString();
        squareroot.toString();

	}
}
