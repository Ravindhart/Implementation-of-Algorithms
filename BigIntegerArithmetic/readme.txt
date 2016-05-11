Project Description:
In this project, develop a program that implements arithmetic with large integers, of arbitrary size. There are 3 levels for this project. All groups are required to complete Level 1. Levels 2 and 3 are optional, and help you to earn excellence credits.

Code base: Java library: Lists, stacks, queues, sets, maps, hashing, trees. Do not use BigInteger, BigNum, or other libraries that implement arbitrary precision arithmetic.

In this document, we will refer to this class as XYZ. Rename it suitably. You must use the following data structure for representing XYZ: Linked list or Array List or Array of long integers, where the digits are in base B. In particular, do not use strings to represent the numbers. Each node of the list stores exactly one long integer. You can choose the value of B and define it globally in your class, for e.g., "final int B = 1000;". In the discussions below, we will use B = 10, using linked lists to represent XYZ. For B = 10, the number 4628 is represented by the list: 8-->2-->6-->4.

Level 1
-------

In level 1, all numbers are non-negative integers. Implement the following methods:

    XYZ(String s): Constructor for XYZ class; takes a string s as parameter, that stores a number in decimal, and creates the XYZ object representing that number. The string can have arbitrary length. In Level 1, the string contains only the numerals 0-9, with no leading zeroes.
    XYZ(Long num): Constructor for XYZ class.
    String toString(): convert the XYZ class object into its equivalent string (in decimal). There should be no leading zeroes in the string.
    XYZ add(XYZ a, XYZ b): sum of two numbers stored as XYZ.
    XYZ subtract(XYZ a, XYZ b): given two XYZ a and b as parameters, representing the numbers n1 and n2 repectively, returns the XYZ corresponding to n1-n2. If you have implemented negative numbers, return the actual answer. If not, then if the result is negative, it returns the XYZ for number 0.
    XYZ product(XYZ a, XYZ b): product of two numbers.
    XYZ power(XYZ a, long n): given an XYZ a, representing the number x and n, returns the BigNum corresponding to x^n (x to the power n). Assume that n is a nonnegative number. Use divide-and-conquer to implement power using O(log n) calls to product and add.
    printList(): Print the base + ":" + elements of the list, separated by spaces. 



Level 2:
--------

Implement Level 1 and the following additional capabilities. Implement negative numbers, so that subtract returns the correct answer instead of 0 when the result is negative. Additional functions to be implemented for Level 2:

    XYZ power(XYZ a, XYZ n): return a^n, where a and n are both XYZ. Here a may be negative, but assume that n is non-negative.
    XYZ divide(XYZ a, XYZ b): Divide a by b result. Fractional part is discarded (take just the quotient). Both a and b may be positive or negative. If b is 0, raise an exception.
    XYZ mod(XYZ a, XYZ b): remainder you get when a is divided by b (a%b). Assume that a is non-negative, and b > 0.
    XYZ squareRoot(XYZ a): return the square root of a (truncated). Use binary search. Assume that a is non-negative. 


Instructions to Execute the files
1. Extract the archive and Copy all the source files to the required directory.
2. Please use below commands to compile the source files
> javac Product.java
> javac Helper.java
> javac LongNumber.java
> javac ArithmeticOperations.java
> javac L1Driver.java
> javac L2Driver.java

3. Execute the files using below commands
> java L1Driver
> java L2Driver: Please provide console input

 