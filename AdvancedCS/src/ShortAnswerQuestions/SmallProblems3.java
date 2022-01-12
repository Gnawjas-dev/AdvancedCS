package ShortAnswerQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class SmallProblems3 {
	int vertical = 0;
	int horizontal = 0;
	ArrayList<String> direction = new ArrayList<String>();
	public SmallProblems3() {
		System.out.println(medium(1324));
		String[] directions = new String[] {"North", "North","South"};
		System.out.println(easy(directions));
	}
	
	public ArrayList<String> easy(String[] directions) {
		
		
		
		
		for(String s : directions) {
			if(s.equals("North")) {
				vertical++;
			}
			if(s.equals("South")) {
				vertical--;
			}
			if(s.equals("West")) {
				horizontal--;
			}
			if(s.equals("East")) {
				horizontal++;
			}
		}
		
		while(vertical!=0 && horizontal!=0) {
			addInstructions();
		}
		
		return direction;
		
	}
	
	private ArrayList<String> addInstructions() {
		// TODO Auto-generated method stub
		
		if(vertical<0) {
			direction.add("South");
			vertical++;
		}
		if(vertical>0) {
			direction.add("North");
			vertical--;
		}
		if(horizontal<0) {
			direction.add("West");
			horizontal++;
		}
		if(horizontal>0) {
			direction.add("East");
			horizontal--;
		}
		
		return direction;
		
	}

	public String medium(int num) {
		TreeSet<Character> list = new TreeSet<>();
		int split=-1;
		String number = Integer.toString(num);
		
		
		char[] arr = number.toCharArray();
		
		char temp;
		
		
		for(int i=arr.length-2;i>0;i--) {
			if(Character.getNumericValue(arr[i])>Character.getNumericValue(arr[i+1])) {
				temp = arr[i];
				arr[i]=arr[i+1];
				arr[i+1]=temp;
				split=i;
					
				for(int j=i+1; j>arr.length;i++) {
					list.add(arr[j]);
					System.out.println("hi");
				}
				break;
			}
		}
		
		number = "";
		for(int i=0; i<split; i++) {
			number+=arr[i];
		}
		for(char c : list) {
			number+=c;
			System.out.println(number);
		}
		return number;
		

	}
	
	// 1 3 2 1
	// 1 3 1 2
	// 4 3 2 3
	// 4 2 3 3
	// 1 3 2 4
	// 1 2 3 4
	// 5 2 3 4
	// 5 3 2 4
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SmallProblems3();
	}

}
