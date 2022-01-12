package ShortAnswerQuestions;

import java.util.ArrayList;
import java.util.Arrays;

public class SmallProblems4 {
	
	public SmallProblems4() {
		
		System.out.println(formattingLegal("(())()") ? "true" : "false");
		ArrayList<Integer>coders = new ArrayList<>();
		coders.add(1);
		coders.add(1);
		coders.add(null)
		System.out.println(coders);
		codefight(2,7,coders);
		System.out.println(coders);
	}
	
	public boolean formattingLegal(String check) {
		
		int open = 0;
		
		for(char c : check.toCharArray()) {
			if(c=='(')	open++;
			if(c==')')	open--;
			if(open<0)	return false;
		}
		
		if(open!=0)
			return false;
		
		return true;
		
		
	}
	
	public void codefight(int chaosIndex, int chaosProficiency, ArrayList<Integer> coders) {

		coders.add(chaosIndex, chaosProficiency);
		boolean battleFound = false;
		while (battleFound) {
			battleFound = false;
			for(int i=0; i<coders.size()-1;i++) {
	//			if(i>0&&coders.get(i)==coders.get(i-1)) {
	//				i--;
	//			}
				if(coders.get(i)==coders.get(i+1)) {
					while(coders.get(i)==coders.get(i+1)) {
						coders.remove(i+1);
					}
					coders.set(i, coders.get(i)+1);
					battleFound = true;
					break;
				}
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SmallProblems4();
	}

}
