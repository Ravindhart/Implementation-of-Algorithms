/**
 * sample driver program for Level2 Big Integer Arithmetic operations
 * 
 * @author Ravindhar Reddy Thallapureddy
 * Last modified on : 5/11/2016 2:43 pm
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class L2Driver {
	public final static String EQUAL_TO ="=";
	public final static String MULITPLY = "*";
	public final static String ADD ="+";
	public final static String SUBTRACT = "-";
	public final static String QUESTION ="?";
	public final static String DIVIDE = "/";
	public final static String REMAINDER ="%";
	public final static String POWER = "^";
	public final static String FACTORIAL = "!";
	public final static String SQAUREROOT ="~";
	public final static String PRINTLIST = ")";
	public final static String COLON = ":";
	public final static String SPACE =" ";
	public final static String EQ ="E";
	public final static String MUL = "M";
	public final static String AD ="A";
	public final static String SUB = "S";
	public final static String Q ="Q";
	public final static String DIV = "D";
	public final static String MOD ="R";
	public final static String POW = "P";
	public final static String FAC = "F";
	public final static String SQUARE ="W";
	public final static String VAL = "V";
	public final static String OUTPUT ="O";
	public final static String LIST ="L";
	public final static String EMPTY="";
	public final static String DOUBLESLASH="\\";
	
	
	public static void main(String[] args) {
          TreeMap<Integer, String> lineCmdMap = new TreeMap<Integer, String>();
          Scanner in = new Scanner(System.in);
          while(in.hasNext()){
        	  Integer line = new Integer(in.nextInt());
        	  String command = in.next();
        	  lineCmdMap.put(line, command);
          }
          Map<String, LongNumber> objMap = new HashMap<String, LongNumber>();
          Set<Integer> lineSet = lineCmdMap.keySet();
          List<Integer> keyList = new ArrayList<Integer>();
          for(Integer line : lineSet){
        	  String cmd = lineCmdMap.get(line);
        	  String modified = findCommand(cmd);
        	  lineCmdMap.put(line, modified);
        	  keyList.add(line);
          }
          populateObjectMap(objMap, lineCmdMap, keyList, lineSet);
          in.close();
	}
	
	static void populateObjectMap(Map<String, LongNumber> objMap, TreeMap<Integer, String> lineMap, List<Integer> keyList, Set<Integer> lineSet){
		for(Integer line : lineSet){
			executeCommands(line, objMap, lineMap, keyList);
		}
	}
	
	static boolean executeCommands(Integer line, Map<String, LongNumber> objMap, TreeMap<Integer, String> lineMap, List<Integer> keyList ){
		String command = lineMap.get(line);
		String[] values = command.split(SPACE);
		switch(values[0]){
		case VAL:
			LongNumber l = new LongNumber(values[2]);
			objMap.put(values[1], l);
			break;
		case AD:
			LongNumber x = objMap.get(values[2]);
			LongNumber y = objMap.get(values[3]);
			LongNumber addRes = ArithmeticOperations.add(x, y);
			objMap.put(values[1], addRes);
			break;
		case MUL:
			LongNumber m1 = objMap.get(values[2]);
			LongNumber m2 = objMap.get(values[3]);
			LongNumber mulRes = ArithmeticOperations.product(m1, m2);
			objMap.put(values[1], mulRes);
			break;
		case SUB:
			LongNumber s1 = objMap.get(values[2]);
			LongNumber s2 = objMap.get(values[3]);
			LongNumber subRes = ArithmeticOperations.subtract(s1, s2);
			objMap.put(values[1], subRes);
			break;
		case DIV:
			LongNumber d1 = objMap.get(values[2]);
			LongNumber d2 = objMap.get(values[3]);
			LongNumber divRes = ArithmeticOperations.divide(d1, d2);
			objMap.put(values[1], divRes);
			break;
		case MOD:
			LongNumber r1 = objMap.get(values[2]);
			LongNumber r2 = objMap.get(values[3]);
			LongNumber remRes = ArithmeticOperations.mod(r1, r2);
			objMap.put(values[1], remRes);
			break;
		case POW:
			LongNumber p1 = objMap.get(values[2]);
			LongNumber p2 = objMap.get(values[3]);
			LongNumber powRes = ArithmeticOperations.power(p1, p2);
			objMap.put(values[1], powRes);
			break;
		case FAC:
			LongNumber fac1 = objMap.get(values[2]);
			LongNumber res = ArithmeticOperations.factorial(fac1);
			objMap.put(values[1], res);
			break;
		case SQUARE:
			LongNumber sq1 = objMap.get(values[2]);
			LongNumber sqRes = ArithmeticOperations.squareRoot(sq1);
			objMap.put(values[1], sqRes);
			break;
		case OUTPUT:
			System.out.println(objMap.get(values[1]));
			break;
		case PRINTLIST:
			LongNumber pl1 = objMap.get(values[2]);
			pl1.printList();
			break;
		case Q:
			LongNumber q1 = objMap.get(values[1]);
			if(!isZero(q1)){
				executeLines(values[2], keyList, objMap, lineMap, line);
			}
			if(isZero(q1)){
				objMap.put(values[1], null);
			}
			break;
		}
		return false;
	}
	static void executeLines(String x, List<Integer> keyList, Map<String, LongNumber> objMap, TreeMap<Integer, String> lineMap, Integer limit){
		Integer line = Integer.parseInt(x);
		boolean goIn = false;
		for(Integer l1 : keyList){
			if (l1.equals(line) || goIn){
				goIn = true;
				executeCommands(l1, objMap, lineMap, keyList);
				if(l1.equals(limit))
					break;
			}
		}
	}
	
	static boolean isZero(LongNumber l){
		if(l == null)
			return true;
		List<Long> number = l.convertedNumber;
		if(number == null || number.size() == 0)
			return true;
		if(number.size() == 1)
			return number.get(0).equals(0);
		return false;	
	}
	static String findCommand(String cmd){
		StringBuilder cmdBuilder = new StringBuilder();
		String[] partials = cmd.split(EQUAL_TO);
		
		if(partials.length == 2){
		if(partials[1].contains(ADD))
			cmdBuilder.append(AD).append(SPACE).append(partials[0]).append(SPACE).append(partials[1].replaceAll(DOUBLESLASH+ADD, SPACE));
		else if(partials[1].contains(MULITPLY))
			cmdBuilder.append(MUL).append(SPACE).append(partials[0]).append(SPACE).append(partials[1].replaceAll(DOUBLESLASH+MULITPLY, SPACE));
		else if(partials[1].contains(SUBTRACT))
			cmdBuilder.append(SUB).append(SPACE).append(partials[0]).append(SPACE).append(partials[1].replaceAll(DOUBLESLASH+SUBTRACT, SPACE));
		else if(partials[1].contains(DIVIDE))
			cmdBuilder.append(DIV).append(SPACE).append(partials[0]).append(SPACE).append(partials[1].replaceAll(DIVIDE, SPACE));
		else if(partials[1].contains(REMAINDER))
			cmdBuilder.append(MOD).append(SPACE).append(partials[0]).append(SPACE).append(partials[1].replaceAll(REMAINDER, SPACE));
		else if(partials[1].contains(POWER))
			cmdBuilder.append(POW).append(SPACE).append(partials[0]).append(SPACE).append(partials[1].replaceAll(DOUBLESLASH+POWER, SPACE));
		else if(partials[1].contains(FACTORIAL))
			cmdBuilder.append(FAC).append(SPACE).append(partials[0]).append(SPACE).append(partials[1].replaceAll(FACTORIAL, EMPTY));
		else if(partials[1].contains(SQAUREROOT))
			cmdBuilder.append(SQUARE).append(SPACE).append(partials[0]).append(SPACE).append(partials[1].replaceAll(SQAUREROOT, EMPTY));
		else
			cmdBuilder.append(VAL).append(SPACE).append(partials[0]).append(SPACE).append(partials[1]);
		}
		else{
			if(cmd.contains(PRINTLIST)){
				cmdBuilder.append(LIST).append(SPACE).append(cmd.replaceFirst(DOUBLESLASH+PRINTLIST, EMPTY));
			}
			else if(cmd.contains(QUESTION)){
				String[] c = cmd.split(DOUBLESLASH+QUESTION);
				cmdBuilder.append(Q).append(SPACE).append(c[0]).append(SPACE).append(c[1].replaceAll(DOUBLESLASH+COLON, SPACE));
			} 
			else{
				cmdBuilder.append(OUTPUT).append(SPACE).append(cmd);
			}
		}
		
		return cmdBuilder.toString();
	}
}
