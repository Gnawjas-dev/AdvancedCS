package ShortAnswerQuestions;

import java.util.ArrayList;

public class Josephus {
	ArrayList<Integer> people = new ArrayList<>();
	public Josephus(int army) {
		for(int i=0; i<army; i++) {
			people.add(i+1);
		}
		calc();
		System.out.println(people.get(0));
	}
	
	//solved by jason, recursively "halves", k=2 only
	public void calc() {
		if(people.size()%2==0) {
			//if even
			for(int i = 1; i < people.size(); i+=2) {
				people.remove(i);
			}
		}
		else {
			//if odd
			people.remove(0);
			for(int i = 0; i < people.size(); i+=2) {
				people.remove(i);
			}
		}	
		if(people.size()!=1) {
			calc();
		} 
	}
	
	//general solution something out of my skillzone, provided by https://www.geeksforgeeks.org/josephus-problem-set-1-a-on-solution/
	public static int josephus(int n, int k){
        if (n == 1)
            return 1;
        else
            /* The position returned by josephus(n - 1, k)
            is adjusted because the recursive call
            josephus(n - 1, k) considers the original
            position k%n + 1 as position 1
            */
            return (josephus(n - 1, k) + k - 1) % n + 1;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//k=2
		new Josephus(100);
		//k=anything, josephus(number, k);
		//System.out.println(josephus(10000, 2));
	}

}
