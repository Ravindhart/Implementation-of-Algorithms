import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver class for implementing string matching
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/11/2016 10:13 pm
 */
public class Driver {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		
		if (args.length > 0) {
			File stringInput = new File(args[0]);
			in = new Scanner(stringInput);
		} else { 
			in = new Scanner(System.in);
		}
        int matches;
		String text=in.next();
        //String text="irravindharlsd;fudslkfjlksdajflksdaj;fklsdfjldksjflksdajflaskd;ur";
        String pattern=in.next();
        //String pattern="ravindhar";
        System.out.println(text.length()+"  "+pattern.length());
		Timer t=new Timer();
		t.start();
		matches=StringMatching.naiveStringMatcher(text, pattern);
		t.end();
		System.out.println("Naive matching: "+t.toString()+" matches: "+matches);
		t.start();
		matches=StringMatching.rabinKarpMatcher(text, pattern);
		t.end();
		System.out.println("rabin Karp matching: "+t.toString()+" matches: "+matches);
		t.start();
		matches=StringMatching.kmpMatcher(text, pattern);
		t.end();
		System.out.println("kmp matching: "+t.toString()+" matches: "+matches);
		t.start();
		matches=BoyerMoore.BMStringMatcher(text.toCharArray(), pattern.toCharArray());
		t.end();
		System.out.println("kmp matching: "+t.toString()+" matches: "+matches);
	}
}
